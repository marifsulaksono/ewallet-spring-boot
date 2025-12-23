package com.marifsulaksono.ewallet.middleware;

import com.marifsulaksono.ewallet.config.RestAuthenticationEntryPoint;
import com.marifsulaksono.ewallet.exception.JwtAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import com.marifsulaksono.ewallet.repository.TokenBlacklistRepository;
import com.marifsulaksono.ewallet.util.jwt.JwtUtil;
import com.marifsulaksono.ewallet.util.mapper.UserPrincipalMapper;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final TokenBlacklistRepository tokenBlacklistRepository;
    private final RestAuthenticationEntryPoint authenticationEntryPoint;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, TokenBlacklistRepository tokenBlacklistRepository,
            RestAuthenticationEntryPoint authenticationEntryPoint) {
        this.jwtUtil = jwtUtil;
        this.tokenBlacklistRepository = tokenBlacklistRepository;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            try {
                if (!jwtUtil.isTokenValid(token)) {
                    throw new JwtAuthenticationException("Invalid or expired token");
                }

                if (tokenBlacklistRepository.existsByToken(token)) {
                    throw new JwtAuthenticationException("Token revoked");
                }

                String id = jwtUtil.extractUserId(token);
                String email = jwtUtil.extractEmail(token);
                String role = jwtUtil.extractRole(token);

                UserPrincipalMapper principal = new UserPrincipalMapper(id, email, role);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        principal,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + role)));

                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (JwtException ex) {
                SecurityContextHolder.clearContext();
                authenticationEntryPoint.commence(request, response,
                        new JwtAuthenticationException("Invalid JWT: " + ex.getMessage()));
                return;
            } catch (AuthenticationException ex) {
                SecurityContextHolder.clearContext();
                authenticationEntryPoint.commence(request, response, ex);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

}
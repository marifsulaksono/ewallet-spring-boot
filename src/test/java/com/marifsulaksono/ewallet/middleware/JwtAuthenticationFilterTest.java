package com.marifsulaksono.ewallet.middleware;

import com.marifsulaksono.ewallet.config.RestAuthenticationEntryPoint;
import com.marifsulaksono.ewallet.exception.JwtAuthenticationException;
import com.marifsulaksono.ewallet.repository.TokenBlacklistRepository;
import com.marifsulaksono.ewallet.util.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

// mvn -Dtest=JwtAuthenticationFilterTest test

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private TokenBlacklistRepository tokenBlacklistRepository;

    @Mock
    private RestAuthenticationEntryPoint authenticationEntryPoint;

    @Mock
    private FilterChain filterChain;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
    }

    @Test
    void doFilterInternal_WithValidToken_ShouldAuthenticate() throws ServletException, IOException {
        String token = "valid.token.here";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtil.isTokenValid(token)).thenReturn(true);
        when(tokenBlacklistRepository.existsByToken(token)).thenReturn(false);
        when(jwtUtil.extractUserId(token)).thenReturn("user1");
        when(jwtUtil.extractEmail(token)).thenReturn("test@example.com");
        when(jwtUtil.extractRole(token)).thenReturn("USER");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(authenticationEntryPoint, never()).commence(any(), any(), any());
    }

    @Test
    void doFilterInternal_WithInvalidToken_ShouldCallEntryPoint() throws ServletException, IOException {
        String token = "invalid.token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtil.isTokenValid(token)).thenReturn(false);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(authenticationEntryPoint).commence(eq(request), eq(response), any(JwtAuthenticationException.class));
        verify(filterChain, never()).doFilter(any(), any());
    }

    @Test
    void doFilterInternal_WithRevokedToken_ShouldCallEntryPoint() throws ServletException, IOException {
        String token = "revoked.token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtil.isTokenValid(token)).thenReturn(true);
        when(tokenBlacklistRepository.existsByToken(token)).thenReturn(true);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(authenticationEntryPoint).commence(eq(request), eq(response), any(JwtAuthenticationException.class));
        verify(filterChain, never()).doFilter(any(), any());
    }
}

package com.marifsulaksono.ewallet.service.impl;

import com.marifsulaksono.ewallet.entity.UserRole;
import com.marifsulaksono.ewallet.exception.ApiException;
import com.marifsulaksono.ewallet.service.AuthService;
import com.marifsulaksono.ewallet.util.jwt.JwtUtil;
import com.marifsulaksono.ewallet.util.mapper.UserMapper;
import com.marifsulaksono.ewallet.dto.request.LoginRequest;
import com.marifsulaksono.ewallet.dto.request.RegisterRequest;
import com.marifsulaksono.ewallet.dto.response.AuthResponse;
import com.marifsulaksono.ewallet.dto.response.UserResponse;
import com.marifsulaksono.ewallet.entity.TokenBlacklist;
import com.marifsulaksono.ewallet.entity.User;
import com.marifsulaksono.ewallet.repository.TokenBlacklistRepository;
import com.marifsulaksono.ewallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final TokenBlacklistRepository tokenBlacklistRepository;

    public UserResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.USER);

        User savedUser = userRepository.save(user);
        return userMapper.mapToResponse(savedUser);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ApiException("Invalid email or password", HttpStatus.BAD_REQUEST));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ApiException("Invalid email or password", HttpStatus.BAD_REQUEST);
        }

        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole().toString());

        return new AuthResponse(token, user.getId(), user.getRole().toString());
    }

    public void logout(String token) {

        Date expiredAt = jwtUtil.extractExpiration(token);

        LocalDateTime expiredAtLdt = expiredAt
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        TokenBlacklist blacklist = new TokenBlacklist();
        blacklist.setToken(token);
        blacklist.setExpiredAt(expiredAtLdt);

        tokenBlacklistRepository.save(blacklist);
    }
}

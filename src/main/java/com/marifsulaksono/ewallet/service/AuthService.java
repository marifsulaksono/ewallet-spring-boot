package com.marifsulaksono.ewallet.service;

import com.marifsulaksono.ewallet.dto.request.LoginRequest;
import com.marifsulaksono.ewallet.dto.request.RegisterRequest;
import com.marifsulaksono.ewallet.dto.response.AuthResponse;
import com.marifsulaksono.ewallet.dto.response.UserResponse;

public interface AuthService {
    UserResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    void logout(String token);
}

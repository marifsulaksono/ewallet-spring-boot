package com.marifsulaksono.ewallet.util.mapper;

import com.marifsulaksono.ewallet.dto.response.UserResponse;
import com.marifsulaksono.ewallet.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    // Mapper Entity to Response DTO
    public UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}

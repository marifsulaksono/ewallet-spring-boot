package com.marifsulaksono.ewallet.service.impl;

import java.util.List;

import com.marifsulaksono.ewallet.util.mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marifsulaksono.ewallet.dto.request.UserPageRequest;
import com.marifsulaksono.ewallet.dto.request.UserRequest;
import com.marifsulaksono.ewallet.dto.response.UserResponse;
import com.marifsulaksono.ewallet.entity.User;
import com.marifsulaksono.ewallet.exception.ApiException;
import com.marifsulaksono.ewallet.repository.UserRepository;
import com.marifsulaksono.ewallet.service.UserService;
import com.marifsulaksono.ewallet.spesification.UserSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ApiException("Email already in use", HttpStatus.CONFLICT);
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole()) // default role
                .build();

        User savedUser = userRepository.save(user);
        return userMapper.mapToResponse(savedUser);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));
        return userMapper.mapToResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::mapToResponse).toList();
    }

    public Page<UserResponse> getAllPaginatedUsers(UserPageRequest request) {
        Specification<User> spec = Specification.allOf(UserSpecification.hasKeyword(request.getSearch()))
                .and(UserSpecification.hasRole(request.getRole()));

        return userRepository.findAll(spec, request.toPageable())
                .map(userMapper::mapToResponse);
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());

        User updatedUser = userRepository.save(user);
        return userMapper.mapToResponse(updatedUser);
    }

    public void updatePassword(Long id, String oldPassword, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));

        if (!user.getPassword().equals(oldPassword)) {
            throw new ApiException("Old password is incorrect", HttpStatus.BAD_REQUEST);
        }
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ApiException("User not found", HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(id);
    }
}

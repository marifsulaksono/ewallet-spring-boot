package com.marifsulaksono.ewallet.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.marifsulaksono.ewallet.dto.request.UserPageRequest;
import com.marifsulaksono.ewallet.dto.request.UserRequest;
import com.marifsulaksono.ewallet.dto.response.UserResponse;

public interface UserService {
    UserResponse createUser(UserRequest request);
    UserResponse getUserById(Long id);
    List<UserResponse> getAllUsers();
    Page<UserResponse> getAllPaginatedUsers(UserPageRequest request);
    UserResponse updateUser(Long id, UserRequest request);
    void updatePassword(Long id, String oldPassword, String newPassword);
    void deleteUser(Long id);
}
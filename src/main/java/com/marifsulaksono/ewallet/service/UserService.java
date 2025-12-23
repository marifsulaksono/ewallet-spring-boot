package com.marifsulaksono.ewallet.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.marifsulaksono.ewallet.dto.request.UserPageRequest;
import com.marifsulaksono.ewallet.dto.request.UserRequest;
import com.marifsulaksono.ewallet.dto.response.UserResponse;

public interface UserService {
    UserResponse createUser(UserRequest request);

    UserResponse getUserById(String id);

    List<UserResponse> getAllUsers();

    Page<UserResponse> getAllPaginatedUsers(UserPageRequest request);

    UserResponse updateUser(String id, UserRequest request);

    void updatePassword(String id, String oldPassword, String newPassword);

    void deleteUser(String id);
}
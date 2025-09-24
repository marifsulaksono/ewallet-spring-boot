package com.marifsulaksono.ewallet.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marifsulaksono.ewallet.dto.request.UserRequest;
import com.marifsulaksono.ewallet.dto.response.ApiResponse;
import com.marifsulaksono.ewallet.dto.response.UserResponse;
import com.marifsulaksono.ewallet.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody UserRequest request) {
        UserResponse user = userService.createUser(request);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.CREATED.value(), "User created successfully", user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "User fetched successfully", user));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Users fetched successfully", users));
    }

    @GetMapping("/paged")
    public ResponseEntity<ApiResponse<Page<UserResponse>>> getAllBooksPaged(
            @PageableDefault(size = 10, page = 0, sort = "id") Pageable pageable
    ) {
        Page<UserResponse> users = userService.getAllPaginatedUsers(pageable);
        return ResponseEntity.ok(
                ApiResponse.success(HttpStatus.OK.value(), "Users retrieved successfully", users)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable Long id,
                                                                @Valid @RequestBody UserRequest request) {
        UserResponse user = userService.updateUser(id, request);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "User updated successfully", user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "User deleted successfully", null));
    }
    
}

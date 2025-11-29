package com.bank.service;

import com.bank.dto.request.UserCreateRequest;
import com.bank.dto.response.UserResponse;
import com.bank.entity.User;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserCreateRequest request);
    UserResponse getUserByUsername(String username);
    boolean authenticate(String username, String password);
    List<User> getAllUsers();
    User getUserById(Long id);
}
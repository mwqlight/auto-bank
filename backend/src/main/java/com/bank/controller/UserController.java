package com.bank.controller;

import com.bank.dto.response.ApiResponse;
import com.bank.dto.response.UserResponse;
import com.bank.entity.User;
import com.bank.service.UserService;
import com.bank.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        // 注意：在实际应用中，这里应该分页并限制访问权限
        // 当前仅用于演示目的
        List<User> users = userService.getAllUsers();
        List<UserResponse> userResponses = users.stream()
                .map(user -> {
                    UserResponse userResponse = new UserResponse();
                    userResponse.setId(user.getId());
                    userResponse.setUsername(user.getUsername());
                    userResponse.setName(user.getName());
                    userResponse.setRole(user.getRole());
                    userResponse.setStatus(user.getStatus());
                    return userResponse;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(ResponseUtil.success("用户列表获取成功", userResponses));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        // 实现根据ID获取用户信息的逻辑
        User user = userService.getUserById(id);
        if (user != null) {
            UserResponse userResponse = new UserResponse();
            userResponse.setId(user.getId());
            userResponse.setUsername(user.getUsername());
            userResponse.setName(user.getName());
            userResponse.setRole(user.getRole());
            userResponse.setStatus(user.getStatus());
            return ResponseEntity.ok(ResponseUtil.success("用户信息获取成功", userResponse));
        } else {
            return ResponseEntity.ok(ResponseUtil.error(404, "用户不存在"));
        }
    }
}
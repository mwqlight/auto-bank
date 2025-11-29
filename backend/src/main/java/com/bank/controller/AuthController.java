package com.bank.controller;

import com.bank.dto.request.UserCreateRequest;
import com.bank.dto.response.ApiResponse;
import com.bank.dto.response.UserResponse;
import com.bank.service.UserService;
import com.bank.util.JwtUtil;
import com.bank.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@RequestBody UserCreateRequest request) {
        UserResponse response = userService.createUser(request);
        return ResponseEntity.ok(ResponseUtil.success("注册成功", response));
    }
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody UserCreateRequest request) {
        boolean isAuthenticated = userService.authenticate(request.getUsername(), request.getPassword());
        
        if (isAuthenticated) {
            String token = jwtUtil.generateToken(request.getUsername());
            return ResponseEntity.ok(ResponseUtil.success("登录成功", token));
        } else {
            return ResponseEntity.badRequest()
                    .body(ResponseUtil.error(401, "用户名或密码错误"));
        }
    }
}
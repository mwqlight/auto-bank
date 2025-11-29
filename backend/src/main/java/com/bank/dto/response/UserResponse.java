package com.bank.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String username;
    private String name;
    private String role;
    private Integer status;
    private LocalDateTime createdAt;
}
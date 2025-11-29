package com.bank.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequest {
    private String username;
    private String password;
    private String name;
}
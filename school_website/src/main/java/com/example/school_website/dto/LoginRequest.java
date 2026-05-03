package com.example.school_website.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}

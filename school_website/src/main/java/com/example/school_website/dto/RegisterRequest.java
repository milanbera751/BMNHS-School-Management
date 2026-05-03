package com.example.school_website.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String username;
    private String password;
    private String Study_in;
    private String address;
    private String mobile;
    private String role;
}

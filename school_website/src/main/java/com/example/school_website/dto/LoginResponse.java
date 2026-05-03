package com.example.school_website.dto;

import lombok.Getter;

@Getter
public class LoginResponse {
    private String token;
    private boolean firstLogin;
    private String role;
    public LoginResponse(String token, boolean firstLogin, String role) {
        this.token = token;
        this.firstLogin = firstLogin;
        this.role = role;
    }

}

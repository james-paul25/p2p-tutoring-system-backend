package com.example.p2pTutoringSystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private Long userId;
    private String message;

    public LoginResponse(Long userId, String message) {
        this.userId = userId;
        this.message = message;
    }
}

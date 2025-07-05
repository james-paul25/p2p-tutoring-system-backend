package com.example.p2pTutoringSystem.dto;


import com.example.p2pTutoringSystem.enumarate.UserRole;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserRegistrationRequest {
    private final String username;
    private final String email;
    private final String password;
}


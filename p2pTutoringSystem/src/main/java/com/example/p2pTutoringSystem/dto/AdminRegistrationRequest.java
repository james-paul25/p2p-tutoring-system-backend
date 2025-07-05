package com.example.p2pTutoringSystem.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AdminRegistrationRequest {
    private final String username;
    private final String email;
    private final String password;
}

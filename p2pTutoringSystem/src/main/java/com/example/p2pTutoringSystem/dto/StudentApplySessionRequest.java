package com.example.p2pTutoringSystem.dto;

import com.example.p2pTutoringSystem.entities.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class StudentApplySessionRequest {
    private LocalDate sessionDate;
    private LocalTime sessionTime;
    private String topic;
    private User tutorUser;
    private User studentUser;
}

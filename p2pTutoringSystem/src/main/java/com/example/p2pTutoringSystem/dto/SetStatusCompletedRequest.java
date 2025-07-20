package com.example.p2pTutoringSystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class SetStatusCompletedRequest {
    private LocalDate sessionDate;
    private LocalTime sessionTime;
}

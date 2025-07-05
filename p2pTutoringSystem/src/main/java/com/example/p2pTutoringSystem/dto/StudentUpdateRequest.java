package com.example.p2pTutoringSystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentUpdateRequest {
    private String firstName;
    private String middleName;
    private String lastName;
    private int yearLevel;
    private long departmentId;
}

package com.example.p2pTutoringSystem.dto;

import com.example.p2pTutoringSystem.entities.Subject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TutorSubjectRequest {
    private long subjectId;
    private double grade;
}

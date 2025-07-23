package com.example.p2pTutoringSystem.projections;

import com.example.p2pTutoringSystem.entities.Tutor;

public interface TutorRatingProjection {
    Tutor getTutor();
    Double getAverageRating();
}

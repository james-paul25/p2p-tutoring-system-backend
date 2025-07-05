package com.example.p2pTutoringSystem.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Rates")
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "rate_id")
    private long rateId;

    @ManyToOne
    @JoinColumn(nullable = false, name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(nullable = false, name = "tutor_id")
    private Tutor tutor;

    @Column(nullable = false, name = "rating")
    private int rating;

    public Rate(Student student, Tutor tutor, int rating) {
        this.student = student;
        this.tutor = tutor;
        this.rating = rating;
    }
}

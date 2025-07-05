package com.example.p2pTutoringSystem.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Table(name = "Tutor_Subject", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"tutor_id", "subject_id"})
})
@Entity
public class TutorSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name= "tutor_subject_id")
    private long tutorSubjectId;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Column(nullable = false, name = "grade")
    private double grade;

    public TutorSubject(Tutor tutor, Subject subject, double grade) {
        this.tutor = tutor;
        this.subject = subject;
        this.grade = grade;
    }

}

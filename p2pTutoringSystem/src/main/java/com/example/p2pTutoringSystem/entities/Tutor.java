package com.example.p2pTutoringSystem.entities;

import com.example.p2pTutoringSystem.enumarate.TutorStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Tutors")
public class Tutor {

    // tutor select subject and put their grade, with 1.7 above can become tutor

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name= "tutor_id")
    private long tutorId;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="subject_id", nullable = false)
    private Subject subject;

    @Column(nullable = false, name="gwa")
    private double gwa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name="tutor_status")
    private TutorStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false, name="created_at")
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    // apply as tutor
    public Tutor(User user, Student student, double gwa, TutorStatus status, Subject subject) {
        this.student = student;
        this.user = user;
        this.gwa = gwa;
        this.status = status;
        this.subject = subject;
    }

}

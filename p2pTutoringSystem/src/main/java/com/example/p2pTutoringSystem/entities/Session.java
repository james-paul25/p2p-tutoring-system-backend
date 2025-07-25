package com.example.p2pTutoringSystem.entities;

import com.example.p2pTutoringSystem.enumarate.SessionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "sessions", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id", "tutor_id", "subject_id"})
})
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "session_id")
    private long sessionId;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(name = "notes")
    private String notes;

    @Column(name="topic")
    private String topic;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "session_status")
    private SessionStatus sessionStatus;

    @Column(nullable = false, name = "session_date")
    private LocalDate sessionDate;

    @Column(nullable = false, name = "session_start_time")
    private LocalTime sessionStartTime;

    @Column(nullable = false, name = "session_end_time")
    private LocalTime sessionEndTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false, name="created_at")
    private Date createdAt;

    @ManyToOne
    @JoinColumn(nullable = false, name = "tutor_userId")
    private User tutorUser;

    @ManyToOne
    @JoinColumn(nullable = false, name = "student_userId")
    private User studentUser;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    public Session(Student student, Tutor tutor, Subject subject, SessionStatus sessionStatus,
                   LocalDate sessionDate, LocalTime sessionStartTime, LocalTime sessionEndTime,
                   String topic, User tutorUser, User studentUser) {
        this.student = student;
        this.tutor = tutor;
        this.subject = subject;
        this.sessionStatus = sessionStatus;
        this.sessionDate = sessionDate;
        this.sessionStartTime = sessionStartTime;
        this.sessionEndTime = sessionEndTime;
        this.topic = topic;
        this.studentUser = studentUser;
        this.tutorUser = tutorUser;
    }

}

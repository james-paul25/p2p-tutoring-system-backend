package com.example.p2pTutoringSystem.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Messages")
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name= "message_id")
    private long messageId;

    @Column(nullable = false, name = "sender_role")
    private String senderRole;

    @ManyToOne
    @JoinColumn(nullable = false, name = "session_id")
    private Session session;

    @ManyToOne
    @JoinColumn(nullable = false, name = "tutor_id")
    private Tutor tutor;

    @ManyToOne
    @JoinColumn(nullable = false, name = "student_id")
    private Student student;

    @Column(columnDefinition = "TEXT", name = "message", nullable = true)
    private String message;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false, name="send_at")
    private Date sendAt;

    @PrePersist
    protected void onCreate() {
        this.sendAt = new Date();
    }

    public Messages(Session session, Tutor tutor, Student student, String message, String senderRole,String filePath, String fileName) {
        this.session = session;
        this.tutor = tutor;
        this.student = student;
        this.message = message;
        this.senderRole = senderRole;
        this.filePath = filePath;
        this.fileName = fileName;
    }


}

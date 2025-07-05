package com.example.p2pTutoringSystem.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name= "student_id")
    private long studentId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "year_level")
    private int yearLevel;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    // to add student while registering user
    public Student(User user) {
        this.user = user;
    }

    public String getFullName() {
        return String.format("%s %s %s", firstName, middleName != null ? middleName : "", lastName).trim();
    }
}

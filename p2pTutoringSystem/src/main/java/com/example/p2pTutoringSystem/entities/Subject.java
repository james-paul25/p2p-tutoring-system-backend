package com.example.p2pTutoringSystem.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "Subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name= "subject_id")
    private long subjectId;

    @Column(nullable = false, name = "subject_name")
    private String subjectName;

    @Column(nullable = false, name = "subject_description")
    private String subjectDescription;


    public Subject(String subjectName, String subjectDescription) {
        this.subjectName = subjectName;
        this.subjectDescription = subjectDescription;
    }

}

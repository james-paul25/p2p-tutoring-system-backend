package com.example.p2pTutoringSystem.entities;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "Departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name= "department_id")
    private long departmentId;

    @Column(nullable = false, name = "department_name")
    private String departmentName;

    public Department(String departmentName) {
        this.departmentName = departmentName;
    }
}

package com.example.p2pTutoringSystem.repositories;

import com.example.p2pTutoringSystem.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface DepartmentRepository extends JpaRepository<Department, Long > {

    Optional<Department> findByDepartmentName(String departmentName);
    Optional<Department> findByDepartmentId(Long departmentId);
}


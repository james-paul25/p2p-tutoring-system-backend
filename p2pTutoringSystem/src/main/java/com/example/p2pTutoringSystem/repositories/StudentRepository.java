package com.example.p2pTutoringSystem.repositories;

import com.example.p2pTutoringSystem.entities.Student;
import com.example.p2pTutoringSystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByStudentId(long StudentId);
    Optional<Student> findByUser(User user);

}

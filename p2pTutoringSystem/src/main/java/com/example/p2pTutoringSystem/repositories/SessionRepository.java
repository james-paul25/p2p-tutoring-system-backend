package com.example.p2pTutoringSystem.repositories;

import com.example.p2pTutoringSystem.entities.Session;
import com.example.p2pTutoringSystem.entities.Student;
import com.example.p2pTutoringSystem.entities.Subject;
import com.example.p2pTutoringSystem.entities.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findBySessionId(Long sessionId);
    Optional<Session> findByStudentAndSubjectAndTutor(Student student, Subject subject, Tutor tutor);
    List<Session> findByStudent(Student student);
    List<Session> findAllByTutor(Tutor tutor);
    List<Session> findAllByStudent(Student student);
}


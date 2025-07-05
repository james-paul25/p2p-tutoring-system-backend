package com.example.p2pTutoringSystem.repositories;

import com.example.p2pTutoringSystem.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    Optional<Subject> findBySubjectId(Long id);
    Optional<Subject> findBySubjectName(String subjectName);

}

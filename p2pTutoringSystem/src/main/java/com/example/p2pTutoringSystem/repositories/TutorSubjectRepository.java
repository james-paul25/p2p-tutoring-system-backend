package com.example.p2pTutoringSystem.repositories;

import com.example.p2pTutoringSystem.entities.Subject;
import com.example.p2pTutoringSystem.entities.Tutor;
import com.example.p2pTutoringSystem.entities.TutorSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface TutorSubjectRepository extends JpaRepository<TutorSubject, Long> {
    Optional<TutorSubject> findByTutorSubjectId(Long id);
    Optional<TutorSubject> findByTutorAndSubject(Tutor tutor, Subject subject);
    Optional<TutorSubject> findByTutor(Tutor tutor);
    List<TutorSubject> findAllByTutor(Tutor tutor);
}

package com.example.p2pTutoringSystem.repositories;

import com.example.p2pTutoringSystem.entities.Tutor;
import com.example.p2pTutoringSystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface TutorRepository extends JpaRepository<Tutor, Long> {
    Optional<Tutor> findByTutorId(long tutorId);
    Optional<Tutor> findByUser_UserId(long tutorId);
    Optional<Tutor> findByUser(User user);
}

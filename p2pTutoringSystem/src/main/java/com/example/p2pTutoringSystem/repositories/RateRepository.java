package com.example.p2pTutoringSystem.repositories;

import com.example.p2pTutoringSystem.entities.Rate;
import com.example.p2pTutoringSystem.entities.Student;
import com.example.p2pTutoringSystem.entities.Tutor;
import com.example.p2pTutoringSystem.projections.TutorRatingProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface RateRepository extends JpaRepository<Rate, Long> {
    Optional<Rate> findByRateId(Long rateId);
    Optional<Rate> findByStudentAndTutor(Student student, Tutor tutor);

    @Query("SELECT r.tutor.tutorId AS tutorId, AVG(r.rating) AS averageRating " +
            "FROM Rate r GROUP BY r.tutor.tutorId")
    List<TutorRatingProjection> findAverageRatingGroupedByTutor();
}

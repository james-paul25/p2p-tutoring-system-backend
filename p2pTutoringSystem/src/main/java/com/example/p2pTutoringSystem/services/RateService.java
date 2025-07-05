package com.example.p2pTutoringSystem.services;

import com.example.p2pTutoringSystem.dto.RatingRequest;
import com.example.p2pTutoringSystem.entities.Rate;
import com.example.p2pTutoringSystem.entities.Student;
import com.example.p2pTutoringSystem.entities.Tutor;
import com.example.p2pTutoringSystem.projections.TutorRatingProjection;
import com.example.p2pTutoringSystem.repositories.RateRepository;
import com.example.p2pTutoringSystem.repositories.StudentRepository;
import com.example.p2pTutoringSystem.repositories.TutorRepository;
import lombok.AllArgsConstructor;
import org.hibernate.query.spi.QueryOptionsAdapter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RateService {

    private RateRepository rateRepository;
    private TutorRepository tutorRepository;
    private StudentRepository studentRepository;

    public String studentRatesTutor(long studentId, long tutorId, RatingRequest ratingRequest) {

        Optional<Student> studentOptional = studentRepository.findByStudentId(studentId);
        Optional<Tutor> tutorOptional = tutorRepository.findByTutorId(tutorId);
        if(!studentOptional.isPresent()) { return "Student not found"; }
        if(!tutorOptional.isPresent()) { return "Tutor not found"; }

        Student student = studentOptional.get();
        Tutor tutor = tutorOptional.get();

        Optional<Rate> rateOptional = rateRepository.findByStudentAndTutor(student, tutor);
        if(rateOptional.isPresent()) { return "You already rate this tutor."; }

        rateRepository.save(new Rate(
                student,
                tutor,
                ratingRequest.getRating()
        ));

        return "You rate this tutor successfully!";
    }

    public List<TutorRatingProjection> getAverageRatingsByTutor() {
        return rateRepository.findAverageRatingGroupedByTutor();
    }
}

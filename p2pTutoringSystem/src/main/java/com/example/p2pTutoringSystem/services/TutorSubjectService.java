package com.example.p2pTutoringSystem.services;

import com.example.p2pTutoringSystem.dto.TutorSubjectRequest;
import com.example.p2pTutoringSystem.entities.Subject;
import com.example.p2pTutoringSystem.entities.Tutor;
import com.example.p2pTutoringSystem.entities.TutorSubject;
import com.example.p2pTutoringSystem.repositories.SubjectRepository;
import com.example.p2pTutoringSystem.repositories.TutorRepository;
import com.example.p2pTutoringSystem.repositories.TutorSubjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TutorSubjectService {

    private final TutorSubjectRepository tutorSubjectRepository;
    private final TutorRepository tutorRepository;
    private final SubjectRepository subjectRepository;

    public String tutorAddSubject(long tutorId, TutorSubjectRequest tutorSubjectRequest) {
        Optional<Subject> subjectOptional = subjectRepository.findBySubjectId(tutorSubjectRequest.getSubjectId());
        Optional<Tutor> tutorOptional = tutorRepository.findByTutorId(tutorId);

        if(!subjectOptional.isPresent()) { return "Subject Not Found"; }
        if(!tutorOptional.isPresent()) { return "Tutor Not Found"; }

        Subject subject = subjectOptional.get();
        Tutor tutor = tutorOptional.get();

        Optional<TutorSubject> tutorSubjectOptional = tutorSubjectRepository.findByTutorAndSubject(tutor, subject);
        if(tutorSubjectOptional.isPresent()) { return "You already have this tutor subject"; }

        if(tutorSubjectRequest.getGrade() > 1.7 || tutorSubjectRequest.getGrade() < 1.0) {
            return "You do not have enough grade";
        }

        tutorSubjectRepository.save(new TutorSubject(
                tutor,
                subject,
                tutorSubjectRequest.getGrade()
        ));

        return "Tutor selected subject successfully!";
    }

    public List<TutorSubject> getAllTutorSubjects() {
        return tutorSubjectRepository.findAll();
    }

    public List<TutorSubject> getTutorSubjectsByTutorId(long tutorId) {
        Optional<Tutor> tutorOptional = tutorRepository.findByTutorId(tutorId);
        if (!tutorOptional.isPresent()) {
            return Collections.emptyList();
        }

        Tutor tutor = tutorOptional.get();
        return tutorSubjectRepository.findAllByTutor(tutor);
    }


}

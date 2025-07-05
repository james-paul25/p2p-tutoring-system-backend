package com.example.p2pTutoringSystem.services;

import com.example.p2pTutoringSystem.dto.StudentApplySessionRequest;
import com.example.p2pTutoringSystem.entities.Session;
import com.example.p2pTutoringSystem.entities.Student;
import com.example.p2pTutoringSystem.entities.Subject;
import com.example.p2pTutoringSystem.entities.Tutor;
import com.example.p2pTutoringSystem.enumarate.SessionStatus;
import com.example.p2pTutoringSystem.repositories.SessionRepository;
import com.example.p2pTutoringSystem.repositories.StudentRepository;
import com.example.p2pTutoringSystem.repositories.SubjectRepository;
import com.example.p2pTutoringSystem.repositories.TutorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SessionService {

    private SessionRepository sessionRepository;
    private StudentRepository studentRepository;
    private SubjectRepository subjectRepository;
    private TutorRepository tutorRepository;

    public String studentApplySession(long tutorId, long studentId, long subjectId,
                                      StudentApplySessionRequest sessionRequest) {
        Optional<Student> studentOptional = studentRepository.findByStudentId(studentId);
        Optional<Subject> subjectOptional = subjectRepository.findBySubjectId(subjectId);
        Optional<Tutor> tutorOptional = tutorRepository.findByTutorId(tutorId);
        if (!studentOptional.isPresent()) { return "Student Not Found"; }
        if (!subjectOptional.isPresent()) { return "Subject Not Found"; }
        if (!tutorOptional.isPresent()) { return "Tutor Not Found"; }

        Student student = studentOptional.get();
        Subject subject = subjectOptional.get();
        Tutor tutor = tutorOptional.get();

        Optional<Session> sessionOptional = sessionRepository.findByStudentAndSubjectAndTutor(student, subject, tutor);
        if (sessionOptional.isPresent()) { return "You already apply in this session"; }

        sessionRepository.save(new Session(
                student,
                tutor,
                subject,
                SessionStatus.PENDING,
                sessionRequest.getSessionDate(),
                sessionRequest.getSessionTime()
        ));

        return "You apply successfully";
    }

    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    public boolean updateStatus(long sessionId, String status) {

        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (sessionOptional.isPresent()) {
            Session session = sessionOptional.get();
            session.setSessionStatus(SessionStatus.valueOf(status));
            sessionRepository.save(session);
            return true;
        }

        return false;
    }

    public List<Session> getSessionByTutor(long tutorId){
        Optional<Tutor> tutorOptional = tutorRepository.findByTutorId(tutorId);
        if(!tutorOptional.isPresent()){ return Collections.emptyList(); }

        Tutor tutor = tutorOptional.get();

        return sessionRepository.findAllByTutor(tutor);
    }

    public List<Session> getSessioByStudent(long studentId){
        Optional<Student> studentOptional = studentRepository.findByStudentId(studentId);
        if(!studentOptional.isPresent()){ return Collections.emptyList(); }
        Student student = studentOptional.get();
        return sessionRepository.findAllByStudent(student);
    }

}

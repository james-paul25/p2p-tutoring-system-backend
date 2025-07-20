package com.example.p2pTutoringSystem.services;

import com.example.p2pTutoringSystem.dto.EditBioRequest;
import com.example.p2pTutoringSystem.dto.EditNoteRequest;
import com.example.p2pTutoringSystem.dto.SetStatusCompletedRequest;
import com.example.p2pTutoringSystem.dto.StudentApplySessionRequest;
import com.example.p2pTutoringSystem.entities.*;
import com.example.p2pTutoringSystem.enumarate.SessionStatus;
import com.example.p2pTutoringSystem.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private UserRepository userRepository;

    public String studentApplySession(
            long tutorId,
            long studentId,
            long subjectId,
            StudentApplySessionRequest sessionRequest) {

        Optional<Student> studentOptional = studentRepository.findByStudentId(studentId);
        Optional<Subject> subjectOptional = subjectRepository.findBySubjectId(subjectId);
        Optional<Tutor> tutorOptional = tutorRepository.findByTutorId(tutorId);
        Optional<User> tutorUserOptional = userRepository.findByUserId(sessionRequest.getTutorUser());
        Optional<User> studentUserOptional = userRepository.findByUserId(sessionRequest.getStudentUser());
        if (!studentOptional.isPresent()) { return "Student Not Found"; }
        if (!subjectOptional.isPresent()) { return "Subject Not Found"; }
        if (!tutorOptional.isPresent()) { return "Tutor Not Found"; }
        if (!tutorUserOptional.isPresent()) { return "Tutor User Not Found"; }
        if (!studentUserOptional.isPresent()) { return "Student User Not Found"; }

        Student student = studentOptional.get();
        Subject subject = subjectOptional.get();
        Tutor tutor = tutorOptional.get();
        User studentUser  = studentUserOptional.get();
        User tutorUser = tutorUserOptional.get();


        Optional<Session> sessionOptional = sessionRepository.findByStudentAndSubjectAndTutor(student, subject, tutor);
        if (sessionOptional.isPresent()) { return "You already apply in this session"; }

        sessionRepository.save(new Session(
                student,
                tutor,
                subject,
                SessionStatus.PENDING,
                sessionRequest.getSessionDate(),
                sessionRequest.getSessionTime(),
                sessionRequest.getTopic(),
                tutorUser,
                studentUser
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

    // getting the session by user
    public List<Session> getSessionByUser(long userId){
        return null;
    }

    public List<Session> getSessionByStudent(long studentId){
        Optional<Student> studentOptional = studentRepository.findByStudentId(studentId);
        if(!studentOptional.isPresent()){ return Collections.emptyList(); }
        Student student = studentOptional.get();
        return sessionRepository.findAllByStudent(student);
    }

    // tutor can edit note
    public String updateNote(long sessionId, EditNoteRequest noteRequest){
        Optional<Session> sessionOptional = sessionRepository.findBySessionId(sessionId);
        if (!sessionOptional.isPresent()) {
            return "Session Not Found";
        }
        Session session = sessionOptional.get();

        session.setNotes(noteRequest.getNotes());
        sessionRepository.save(session);

        return "Note saved successfully";
    }

    public String updateStatusComplete(long sessionId) {
        Optional<Session> sessionOptional = sessionRepository.findBySessionId(sessionId);

        if (!sessionOptional.isPresent()) {
            return "Session Not Found";
        }

        Session session = sessionOptional.get();

        if (session.getSessionStatus() == SessionStatus.COMPLETED) {
            return "Session Already Completed";
        }

        LocalDate sessionDate = session.getSessionDate();
        LocalTime sessionTime = session.getSessionTime();

        LocalDateTime sessionDateTime = LocalDateTime.of(sessionDate, sessionTime);
        LocalDateTime now = LocalDateTime.now();

        if (sessionDateTime.isBefore(now) || sessionDateTime.isEqual(now)) {
            session.setSessionStatus(SessionStatus.COMPLETED);
            sessionRepository.save(session);
            return "Session Completed";
        } else {
            return "Session Not Yet Due";
        }
    }


}

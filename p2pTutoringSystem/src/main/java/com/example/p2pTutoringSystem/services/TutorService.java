package com.example.p2pTutoringSystem.services;

import com.example.p2pTutoringSystem.dto.ApplyAsTutor;
import com.example.p2pTutoringSystem.entities.Student;
import com.example.p2pTutoringSystem.entities.Tutor;
import com.example.p2pTutoringSystem.entities.User;
import com.example.p2pTutoringSystem.enumarate.TutorStatus;
import com.example.p2pTutoringSystem.enumarate.UserRole;
import com.example.p2pTutoringSystem.repositories.StudentRepository;
import com.example.p2pTutoringSystem.repositories.TutorRepository;
import com.example.p2pTutoringSystem.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TutorService {

    private TutorRepository tutorRepository;
    private StudentRepository studentRepository;
    private UserRepository userRepository;

    public String applyAsTutor(long userId, long studentID, ApplyAsTutor applyAsTutor) {
        Optional<Tutor> tutorExist = tutorRepository.findByUser_UserId(userId);
        if(tutorExist.isPresent()) {
            return "You have already applied as tutor";
        }

        Optional<User> userOptional = userRepository.findByUserId(userId);
        Optional<Student> studentOptional = studentRepository.findByStudentId(studentID);
        if (!userOptional.isPresent()) { return "User not found."; }
        if (!studentOptional.isPresent()) { return "Student not found."; }

        User user = userOptional.get();
        Student student = studentOptional.get();

        double gwa = applyAsTutor.getGwa();
        double requiredGwa = 1.8;
        if(gwa >= requiredGwa){ return "Your GPA must be at least " +
                requiredGwa + " to apply as a tutor."; }

        tutorRepository.save(new Tutor(
                user,
                student,
                gwa,
                TutorStatus.PENDING
        ));

        user.setRole(UserRole.TUTOR);
        userRepository.save(user);

        return "Your application is in progress.";
    }

    public List<Tutor> getAllTutors() {
        return tutorRepository.findAll();
    }

    public Tutor getTutorByUser(long userId) {
        Optional<User> userOptional = userRepository.findByUserId(userId);
        if(!userOptional.isPresent()) { return null; }

        Optional<Tutor> tutorOptional = tutorRepository.findByUser_UserId(userId);
        if(!tutorOptional.isPresent()) { return null; }

        return tutorOptional.get();
    }

    public Tutor approvedTutor(long tutorId) {
        Optional<Tutor> tutorOptional = tutorRepository.findByTutorId(tutorId);
        if(!tutorOptional.isPresent()) { return null; }

        Tutor tutor = tutorOptional.get();

        tutor.setStatus(TutorStatus.APPROVED);

        return tutorRepository.save(tutor);
    }

    public Tutor rejectTutor(long tutorId) {
        Optional<Tutor> tutorOptional = tutorRepository.findByTutorId(tutorId);
        if(!tutorOptional.isPresent()) { return null; }
        Tutor tutor = tutorOptional.get();
        tutor.setStatus(TutorStatus.REJECTED);
        tutor.getUser().setRole(UserRole.STUDENT);
        return tutorRepository.save(tutor);
    }

    public String deleteTutor(long tutorId){
        Optional<Tutor> tutorOptional = tutorRepository.findByTutorId(tutorId);
        if(!tutorOptional.isPresent()) { return "Tutor not found."; }
        Tutor tutor = tutorOptional.get();
        tutorRepository.delete(tutor);
        return "Tutor deleted";
    }


}

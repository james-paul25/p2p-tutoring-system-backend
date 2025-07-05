package com.example.p2pTutoringSystem.services;

import com.example.p2pTutoringSystem.dto.AddSubjectRequest;
import com.example.p2pTutoringSystem.entities.Subject;
import com.example.p2pTutoringSystem.repositories.SubjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SubjectService {

    private SubjectRepository subjectRepository;

    public String addSubject(AddSubjectRequest addSubjectRequest) {
        Optional<Subject> subjectOptional = subjectRepository.findBySubjectName(addSubjectRequest.getSubjectName());

        if (subjectOptional.isPresent()) {
            Subject subject = subjectOptional.get();
            return subject.getSubjectName() + " subject already exists";
        }

        subjectRepository.save(new Subject(
                addSubjectRequest.getSubjectName(),
                addSubjectRequest.getSubjectDescription()
        ));

        return addSubjectRequest.getSubjectName() + " added successfully";
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public String deleteSubjectById(long subjectId) {
        Optional<Subject> subjectOptional = subjectRepository.findBySubjectId(subjectId);
        if (!subjectOptional.isPresent()) { return "Subject not found"; }
        subjectRepository.deleteById(subjectId);
        return "Subject deleted successfully";
    }

    public String editSubjectById(long subjectId, AddSubjectRequest addSubjectRequest) {
        Optional<Subject> subjectOptional = subjectRepository.findBySubjectId(subjectId);
        if (subjectOptional.isEmpty()) { return "Subject not found"; }
        Subject subject = subjectOptional.get();
        subject.setSubjectName(addSubjectRequest.getSubjectName());
        subject.setSubjectDescription(addSubjectRequest.getSubjectDescription());
        subjectRepository.save(subject);
        return "Subject edited successfully";
    }
}

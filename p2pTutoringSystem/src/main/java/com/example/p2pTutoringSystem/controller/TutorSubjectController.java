package com.example.p2pTutoringSystem.controller;

import com.example.p2pTutoringSystem.dto.TutorSubjectRequest;
import com.example.p2pTutoringSystem.entities.Subject;
import com.example.p2pTutoringSystem.entities.TutorSubject;
import com.example.p2pTutoringSystem.repositories.SubjectRepository;
import com.example.p2pTutoringSystem.services.TutorSubjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@AllArgsConstructor
@RestController()
@RequestMapping("api/v1/tutor-subjects")
public class TutorSubjectController {

    private final TutorSubjectService tutorSubjectService;

    @PostMapping("/tutor-add-subject/{tutorId}")
    public ResponseEntity<String> tutorAddSubject(
            @PathVariable long tutorId,
            @RequestBody TutorSubjectRequest tutorSubjectRequest) {
        String response = tutorSubjectService.tutorAddSubject(tutorId, tutorSubjectRequest);

        if(response.equals("Tutor selected subject successfully!")){
            return ResponseEntity.ok("Tutor selected subject successfully!");
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/get-all-subjects")
    public List<TutorSubject> getTutorSubjectService() {
        return tutorSubjectService.getAllTutorSubjects();
    }

    @GetMapping("/get-tutor-subjects/{tutorId}")
    public ResponseEntity<List<TutorSubject>> getTutorSubjects(@PathVariable Long tutorId) {
        List<TutorSubject> subjects = tutorSubjectService.getTutorSubjectsByTutorId(tutorId);

        if (subjects.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(subjects);
    }
}

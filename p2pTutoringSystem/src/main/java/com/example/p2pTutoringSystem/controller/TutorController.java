package com.example.p2pTutoringSystem.controller;

import com.example.p2pTutoringSystem.dto.ApplyAsTutor;
import com.example.p2pTutoringSystem.entities.Tutor;
import com.example.p2pTutoringSystem.services.TutorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@AllArgsConstructor
@RestController()
@RequestMapping("api/v1/tutors")
public class TutorController {

    private final TutorService tutorService;


    @PostMapping("/apply/{userId}/{studentId}")
    public ResponseEntity<?> applyAsTutor(
            @PathVariable long userId,
            @PathVariable long studentId,
            @RequestBody ApplyAsTutor applyAsTutor) {

        String response = tutorService.applyAsTutor(userId, studentId, applyAsTutor);

        if(response.equals("Your application is in progress.")){
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/get-all-tutors")
    public List<Tutor> getAllTutors() {
        return tutorService.getAllTutors();
    }

    @GetMapping("/get-tutor-by-user/{userId}")
    public ResponseEntity<?> getTutorByUserId(@PathVariable long userId) {
        Tutor tutor = tutorService.getTutorByUser(userId);

        if(tutor != null) {
            return ResponseEntity.ok(tutor);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tutor not found for the given userId.");
        }
    }

    @PutMapping("/approved/{tutorId}")
    public ResponseEntity<?> approveTutor(@PathVariable long tutorId) {
        Tutor tutor = tutorService.approvedTutor(tutorId);
        if(tutor != null) { return ResponseEntity.ok(tutor); }
        else { return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tutor not found."); }
    }

    @PutMapping("/rejected/{tutorId}")
    public ResponseEntity<?> rejectTutor(@PathVariable long tutorId) {
        Tutor tutor = tutorService.rejectTutor(tutorId);
        if(tutor != null) { return ResponseEntity.ok(tutor); }
        else { return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tutor not found."); }
    }

    @DeleteMapping("/delete-tutor/{tutorId}")
    public ResponseEntity<?> deleteTutor(@PathVariable long tutorId) {
        String response = tutorService.deleteTutor(tutorId);
        if(response.equals("Tutor deleted")){ return ResponseEntity.ok("Tutor deleted."); }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tutor not found.");
    }
}

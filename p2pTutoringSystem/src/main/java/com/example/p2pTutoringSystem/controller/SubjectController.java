package com.example.p2pTutoringSystem.controller;


import com.example.p2pTutoringSystem.dto.AddSubjectRequest;
import com.example.p2pTutoringSystem.entities.Subject;
import com.example.p2pTutoringSystem.services.SubjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173", "https://bisu-p2p-tutoring.vercel.app/"}, allowCredentials = "true", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController()
@RequestMapping("api/v1/subjects")
@AllArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping("/add-subject")
    public ResponseEntity<String> addSubject(@RequestBody AddSubjectRequest addSubjectRequest) {
        String response = subjectService.addSubject(addSubjectRequest);

        if(response.equals(addSubjectRequest.getSubjectName() + " added successfully")){
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/get-all-subjects")
    public List<Subject> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @DeleteMapping("/delete-subject/{subjectId}")
    public ResponseEntity<?> deleteSubject(@PathVariable int subjectId) {
        String response = subjectService.deleteSubjectById(subjectId);
        if(response.equals("Subject deleted successfully")){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @PutMapping("/edit/{subjectId}")
    public ResponseEntity<?> editSubjectById(
            @PathVariable long subjectId,
            @RequestBody AddSubjectRequest subjectRequest) {
        String response = subjectService.editSubjectById(subjectId, subjectRequest);
        if(response.equals("Subject edited successfully")){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

}

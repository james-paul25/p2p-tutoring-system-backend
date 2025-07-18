package com.example.p2pTutoringSystem.controller;

import com.example.p2pTutoringSystem.dto.EditNoteRequest;
import com.example.p2pTutoringSystem.dto.SessionStatusUpdateRequest;
import com.example.p2pTutoringSystem.dto.StudentApplySessionRequest;
import com.example.p2pTutoringSystem.entities.Session;
import com.example.p2pTutoringSystem.services.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController()
@RequestMapping("api/v1/sessions")
@AllArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @PostMapping("/students-apply-session/{tutorId}/{subjectId}/{studentId}")
    public ResponseEntity<String> studentApplySession(
            @PathVariable long tutorId,
            @PathVariable long studentId,
            @PathVariable long subjectId,
            @RequestBody StudentApplySessionRequest studentApplySessionRequest) {

        String response = sessionService.studentApplySession(tutorId, studentId, subjectId, studentApplySessionRequest);

         if(response.equals("You apply successfully")){
             return ResponseEntity.ok(response);
         } else {
             return ResponseEntity.badRequest().body(response);
         }
    }

    @GetMapping("/get-all-sessions")
    public List<Session> getAllSessions(){
        return sessionService.getAllSessions();
    }


    @PutMapping("/update-status/{sessionId}")
    public ResponseEntity<?> updateSessionStatus(
            @PathVariable long sessionId,
            @RequestBody SessionStatusUpdateRequest sessionStatusUpdateRequest) {

        boolean updated = sessionService.updateStatus(sessionId, sessionStatusUpdateRequest.getStatus());

        if(updated){
            return ResponseEntity.ok(sessionStatusUpdateRequest);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Session not found.");
        }
    }

    @GetMapping("/get-session-by-tutor/{tutorId}")
    public ResponseEntity<List<Session>> getSessionByTutorId(
            @PathVariable long tutorId){
        List<Session> session = sessionService.getSessionByTutor(tutorId);

        if(session.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.ok(session);
        }
    }

    @GetMapping("/get-session-by-student/{studentId}")
    public ResponseEntity<List<Session>> getSessionByStudentId(
            @PathVariable long studentId){
        List<Session> session = sessionService.getSessionByStudent(studentId);
        if(session.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(session);
    }

    @PutMapping("/edit-note/{sessionId}")
    public ResponseEntity<?> editNote(
            @PathVariable long sessionId,
            @RequestBody EditNoteRequest editNoteRequest) {

        String response = sessionService.updateNote(sessionId, editNoteRequest);
        if(response.equals("Note saved successfully")){
           return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}

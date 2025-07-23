package com.example.p2pTutoringSystem.controller;

import com.example.p2pTutoringSystem.dto.MessageRequest;
import com.example.p2pTutoringSystem.entities.Messages;
import com.example.p2pTutoringSystem.services.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:5173", "https://bisu-p2p-tutoring.vercel.app/"}, allowCredentials = "true", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController()
@RequestMapping("api/v1/messages")
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/send/{sessionId}/{tutorId}/{studentId}")
    public ResponseEntity<String> sendMessage(
            @PathVariable Long sessionId,
            @PathVariable Long tutorId,
            @PathVariable Long studentId,
            @RequestBody MessageRequest messageRequest) {

        try {

            String response = messageService.sendMessage(sessionId, tutorId, studentId, messageRequest);

            if(response.equals("Message sent successfully")){
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid studentId: Not a number");
        }
    }

    @PostMapping("/send-file/{sessionId}/{tutorId}/{studentId}")
    public ResponseEntity<Map<String, String>> sendMessage(
            @PathVariable Long sessionId,
            @PathVariable Long tutorId,
            @PathVariable Long studentId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("senderRole") String senderRole,
            @RequestParam("sendAt") String sendAt
    ) {
        try {
            String filePath = messageService.sendMessage(sessionId, tutorId, studentId, file, senderRole, sendAt);

            if (!filePath.equals("Failed to send message") && !filePath.equals("Session not found")
                    && !filePath.equals("Student not found") && !filePath.equals("Tutor not found")) {

                Map<String, String> response = new HashMap<>();
                response.put("message", "File sent successfully");
                response.put("filePath", filePath);

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(Map.of("message", filePath));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Failed to send message"));
        }
    }


    @GetMapping("/get-messages/{sessionId}")
    public List<Messages> getMessageBySession(@PathVariable Long sessionId) {
        return messageService.getMessagesBySession(sessionId);
    }

}

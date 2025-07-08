package com.example.p2pTutoringSystem.controller;


import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController()
@RequestMapping("api/v1/auth")
@AllArgsConstructor
public class AuthController {

    @GetMapping("/check")
    public ResponseEntity<?> checkSession(HttpSession session) {
        Object user = session.getAttribute("user");

        if (user != null) {
            return ResponseEntity.ok(user);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in.");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutSession(HttpSession session) {
        session.invalidate();

        return ResponseEntity.ok("Logged out.");
    }
}

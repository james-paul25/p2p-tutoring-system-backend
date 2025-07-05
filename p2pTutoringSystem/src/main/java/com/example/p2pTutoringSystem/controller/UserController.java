package com.example.p2pTutoringSystem.controller;

import com.example.p2pTutoringSystem.dto.EmailLoginRequest;
import com.example.p2pTutoringSystem.dto.LoginResponse;
import com.example.p2pTutoringSystem.dto.UserRegistrationRequest;
import com.example.p2pTutoringSystem.entities.Student;
import com.example.p2pTutoringSystem.entities.User;
import com.example.p2pTutoringSystem.services.StudentService;
import com.example.p2pTutoringSystem.services.UserRegistrationService;
import com.example.p2pTutoringSystem.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController()
@RequestMapping(path = "api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRegistrationService userRegistrationService;
    private final StudentService studentService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody EmailLoginRequest request) {
        boolean isLoggedInUsingEmail = userService.isLogin(request.getEmail(), request.getPassword());
        if (isLoggedInUsingEmail) {
            User user = userService.getUserByEmail(request.getEmail());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("role", user.getRole());
            response.put("userId", user.getUserId());
            response.put("username", user.getUsername());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).body("Invalid credentials.");
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        String response = userRegistrationService.registerUser(userRegistrationRequest);
        if (response.equals("User registered successfully!")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(response);
        }
    }

    @PostMapping("/emailExist")
    public boolean emailExists(@RequestBody String email) {
        return userService.emailExists(email);
    }

    @GetMapping("/get-student-info/{userId}")
    public ResponseEntity<?> getStudentInfo(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
    
            Student student = studentService.getStudentByUser(user);

            if (student != null) {
                return ResponseEntity.ok(student);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found for the given userId.");
            }
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found for the given userId.");
        }
    }

    @GetMapping("/get-all-users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        String response = userService.deleteUserById(userId);
        if(response.equals("User deleted successfully!")) {
            return ResponseEntity.ok("User deleted successfully!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}

package com.example.p2pTutoringSystem.controller;

import com.example.p2pTutoringSystem.dto.AdminRegistrationRequest;
import com.example.p2pTutoringSystem.dto.EmailLoginRequest;
import com.example.p2pTutoringSystem.dto.UserRegistrationRequest;
import com.example.p2pTutoringSystem.entities.Admin;
import com.example.p2pTutoringSystem.entities.User;
import com.example.p2pTutoringSystem.services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController()
@RequestMapping("api/v1/admin")
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/registration")
    public ResponseEntity<String> registerAdmin(@RequestBody AdminRegistrationRequest adminRegistrationRequest) {
        String response = adminService.register(adminRegistrationRequest);
        if (response.equals("Admin registered successfully!")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody EmailLoginRequest request) {
        boolean isLoggedInUsingEmail = adminService.isLogin(request.getEmail(), request.getPassword());
        if (isLoggedInUsingEmail) {
            Admin admin = adminService.getAdminByEmail(request.getEmail());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("adminId", admin.getAdminId());
            response.put("username", admin.getUsername());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).body("Invalid credentials.");
    }

    @GetMapping("/get-admin/{adminId}")
    public List<Admin> getAdminByAdminId(@PathVariable long adminId) {
        return adminService.getAdminByAdminId(adminId);
    }
}

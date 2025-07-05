package com.example.p2pTutoringSystem.controller;

import com.example.p2pTutoringSystem.dto.StudentUpdateRequest;
import com.example.p2pTutoringSystem.entities.Student;
import com.example.p2pTutoringSystem.repositories.StudentRepository;
import com.example.p2pTutoringSystem.repositories.UserRepository;
import com.example.p2pTutoringSystem.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@AllArgsConstructor
@RestController()
@RequestMapping("api/v1/students")
public class StudentController {

    private StudentService studentService;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    @PutMapping("/update-student/{studentId}")
    public ResponseEntity<?> updateStudentProfile(
            @PathVariable Long studentId,
            @RequestBody StudentUpdateRequest studentUpdateRequest) {
        String response = studentService.updateStudentProfile(studentId, studentUpdateRequest);
        if (response.equals("Student profile updated successfully")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/get-all-students")
    public List<Student> getAllStudents() {
        List<Student> students = studentService.getStudentList();

        return students.stream()
                .filter(student -> "STUDENT".equalsIgnoreCase(String.valueOf(student.getUser().getRole())))
                .collect(Collectors.toList());
    }



}

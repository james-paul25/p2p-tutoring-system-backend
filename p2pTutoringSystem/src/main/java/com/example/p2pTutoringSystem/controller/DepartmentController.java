package com.example.p2pTutoringSystem.controller;

import com.example.p2pTutoringSystem.dto.AddDepartmentRequest;
import com.example.p2pTutoringSystem.entities.Department;
import com.example.p2pTutoringSystem.services.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController()
@RequestMapping("api/v1/departments")
@AllArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping("/add")
    public ResponseEntity<String> addDepartment(@RequestBody AddDepartmentRequest addDepartmentRequest) {
        String response = departmentService.addDepartment(addDepartmentRequest);
        if (response.equals("Department already exists")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all-department")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    //admin
    @DeleteMapping("/delete/{departmentId}")
    public ResponseEntity<?> deleteDepartmentById(@PathVariable Long departmentId) {
        String response = departmentService.deleteDepartmentById(departmentId);
        if(response.equals("Department deleted successfully")) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @PutMapping("/edit/{departmentId}")
    public ResponseEntity<?> editDepartmentNameById(
            @PathVariable Long departmentId,
            @RequestBody AddDepartmentRequest departmentRequest) {
        String response = departmentService.editDepartmentById(departmentId, departmentRequest.getDepartmentName());
        if(response.equals("Department edited successfully")) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }
}

package com.example.p2pTutoringSystem.services;

import com.example.p2pTutoringSystem.dto.AddDepartmentRequest;
import com.example.p2pTutoringSystem.entities.Department;
import com.example.p2pTutoringSystem.repositories.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public String addDepartment(Department department) {
       Optional<Department> departmentExist = departmentRepository.findByDepartmentName(department.getDepartmentName());

       if (departmentExist.isPresent()) {
           Department department1 = departmentExist.get();
           return department1.getDepartmentName() + " Department Already Exists";
       }
       departmentRepository.save(department);
       return "Department Added";
    }

    public String addDepartment(AddDepartmentRequest addDepartmentRequest) {
        String result = addDepartment(new Department(addDepartmentRequest.getDepartmentName()));

        return result.isEmpty() ? "Department registered successfully!" : result;
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public String deleteDepartmentById(long departmentId){
        Optional<Department> departmentExist = departmentRepository.findByDepartmentId(departmentId);
        if(departmentExist.isEmpty()) { return "Department not found"; }
        departmentRepository.delete(departmentExist.get());
        return "Department deleted successfully";
    }

    public String editDepartmentById(long departmentId, String departmentName) {
        Optional<Department> departmentExist = departmentRepository.findByDepartmentId(departmentId);
        if(departmentExist.isEmpty()) { return "Department not found"; }

        Department department = departmentExist.get();
        department.setDepartmentName(departmentName);
        departmentRepository.save(department);
        return "Department edited successfully";
    }

}

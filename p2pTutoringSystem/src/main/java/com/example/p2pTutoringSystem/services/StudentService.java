package com.example.p2pTutoringSystem.services;

import com.example.p2pTutoringSystem.dto.EditBioRequest;
import com.example.p2pTutoringSystem.dto.StudentUpdateRequest;
import com.example.p2pTutoringSystem.entities.Department;
import com.example.p2pTutoringSystem.entities.Student;
import com.example.p2pTutoringSystem.entities.User;
import com.example.p2pTutoringSystem.repositories.DepartmentRepository;
import com.example.p2pTutoringSystem.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class StudentService {

    private StudentRepository studentRepository;
    private DepartmentRepository departmentRepository;

    public String updateStudentProfile (long studentId, StudentUpdateRequest studentUpdateRequest) {

        Optional<Student> studentOptional = studentRepository.findByStudentId(studentId);
        if (!studentOptional.isPresent()) {
           return "Student not found";
        }

        Optional<Department> departmentOptional = departmentRepository.findByDepartmentId(
                studentUpdateRequest.getDepartmentId());
        if(!departmentOptional.isPresent()) {
            return "Department not found";
        }

        if(studentUpdateRequest.getYearLevel() > 4 ||  studentUpdateRequest.getYearLevel() < 1) {
            return "Invalid year level";
        }

        Student student = studentOptional.get();
        Department department = departmentOptional.get();

        student.setFirstName(studentUpdateRequest.getFirstName());
        student.setMiddleName(studentUpdateRequest.getMiddleName());
        student.setLastName(studentUpdateRequest.getLastName());
        student.setYearLevel(studentUpdateRequest.getYearLevel());
        student.setDepartment(department);

        studentRepository.save(student);

        return "Student profile updated successfully";
    }

    public String updateBio(long studentId, EditBioRequest bioRequest){

        Optional<Student> studentOptional = studentRepository.findByStudentId(studentId);
        if (!studentOptional.isPresent()) {
            return "Student not found";
        }

        Student student = studentOptional.get();

        student.setBio(bioRequest.getBio());

        studentRepository.save(student);

        return "Bio edited successfully";
    }

    public Student getStudentByUser(User user) {
        return studentRepository.findByUser(user)
                .orElse(null);
    }

    public List<Student> getStudentList(){
        return studentRepository.findAll();
    }

}

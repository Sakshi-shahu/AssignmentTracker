package com.example.AssignmentTracker.service;

import com.example.AssignmentTracker.Dto.StudentRequest;
import com.example.AssignmentTracker.Dto.StudentResponse;
import com.example.AssignmentTracker.entity.Student;
import org.springframework.stereotype.Service;

@Service
public interface StudentService {

    Student addStudent(StudentRequest request);

    Student updateStudent(Long id, StudentRequest request);

    void deleteStudent(Long id);

    StudentResponse getStudentProfile(Long id);
}

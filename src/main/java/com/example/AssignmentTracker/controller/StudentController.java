package com.example.AssignmentTracker.controller;


import com.example.AssignmentTracker.entity.Assignment;
import com.example.AssignmentTracker.entity.Student;
import com.example.AssignmentTracker.service.StudentAssignmentService;
import com.example.AssignmentTracker.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final StudentAssignmentService assignmentService;


    // 1. View own profile
    @GetMapping("profile/{studentId}")
    public ResponseEntity<Student> getProfile(@PathVariable Long studentId) {
        Student student = studentService.getStudent(studentId);
        return ResponseEntity.ok(student);
    }


    // 2. View assignments assigned to student
    @GetMapping("/{studentId}/assignments")
    public ResponseEntity<List<Assignment>> getAssignedAssignments(@PathVariable Long studentId) {

        List<Assignment> assignments = assignmentService.getAssignments(studentId);
        return ResponseEntity.ok(assignments);
    }


    // 3. View assignment details
    @GetMapping("/assignments/{assignmentId}")
    public ResponseEntity<Assignment> getAssignmentDetails(@PathVariable Long assignmentId) {
        Assignment assignment = assignmentService.getAssignment(assignmentId);
        return ResponseEntity.ok(assignment);
    }
}
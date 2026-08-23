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


    @GetMapping("profile/{studentId}")
    public ResponseEntity<Student> getProfile(@PathVariable Long studentId) {
        Student student = studentService.getStudent(studentId);
        return ResponseEntity.ok(student);
    }
//done by nikhil
    @GetMapping("profiless/{studentId}")
    public ResponseEntity<List<Student>> getAllStudent() {
        List<Student> student = studentService.getAllStudent();
        return ResponseEntity.ok(student);
    }



    @GetMapping("assignments/{course}")
    public ResponseEntity<List<Assignment>> getAssignedAssignments(@PathVariable String course) {
        List<Assignment> assignments = assignmentService.getAssignmentsByCourse(course);
        return ResponseEntity.ok(assignments);
    }


//use by nhikhil
    @GetMapping("assignments/details")
    public ResponseEntity<List<Assignment>> getAssignmentDetails() {
        List<Assignment> assignment = assignmentService.getAssignmentAll();
        return ResponseEntity.ok(assignment);
    }
}
package com.example.AssignmentTracker.controller;

import com.example.AssignmentTracker.Dto.AssignmentSubmissionResponse;
import com.example.AssignmentTracker.Dto.StudentResponse;
import com.example.AssignmentTracker.service.AssignmentSubmissionService;
import com.example.AssignmentTracker.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final AssignmentSubmissionService submissionService;

    @GetMapping("/{studentId}/profile")
    public ResponseEntity<StudentResponse> getProfile(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentService.getStudentProfile(studentId));
    }



    @GetMapping("/{studentId}/assignments/{assignmentId}/status")
    public ResponseEntity<String> getSubmissionStatus(@PathVariable Long studentId,
                                                      @PathVariable Long assignmentId) {
        AssignmentSubmissionResponse response = submissionService.getSubmission(studentId, assignmentId);
        return ResponseEntity.ok(response.getStatus());
    }

    @GetMapping("/{studentId}/assignments/{assignmentId}/feedback")
    public ResponseEntity<String> getFeedback(@PathVariable Long studentId,
                                              @PathVariable Long assignmentId) {
        AssignmentSubmissionResponse response = submissionService.getSubmission(studentId, assignmentId);
        return ResponseEntity.ok("Marks: " + response.getMarks() + ", Feedback: " + response.getFeedback());
    }


}

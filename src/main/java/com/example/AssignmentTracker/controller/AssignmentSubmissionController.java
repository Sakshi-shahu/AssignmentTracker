package com.example.AssignmentTracker.controller;

import com.example.AssignmentTracker.Dto.AssignmentSubmissionRequest;
import com.example.AssignmentTracker.Dto.AssignmentSubmissionResponse;
import com.example.AssignmentTracker.service.AssignmentSubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students/{studentId}/assignments/{assignmentId}")
@RequiredArgsConstructor
public class AssignmentSubmissionController {

    private final AssignmentSubmissionService submissionService;

    @PostMapping("/submissions")
    public ResponseEntity<AssignmentSubmissionResponse> submitAssignment(
            @PathVariable Long studentId,
            @PathVariable Long assignmentId,
            @RequestBody AssignmentSubmissionRequest request) {
        return ResponseEntity.ok(submissionService.submitAssignment(studentId, assignmentId, request));
    }

    @PutMapping("/submission")
    public ResponseEntity<AssignmentSubmissionResponse> updateSubmission(
            @PathVariable Long studentId,
            @PathVariable Long assignmentId,
            @RequestBody AssignmentSubmissionRequest request) {
        return ResponseEntity.ok(submissionService.updateSubmission(studentId, assignmentId, request));
    }

    @GetMapping("/submissions")
    public ResponseEntity<List<AssignmentSubmissionResponse>> getStudentSubmissions(@PathVariable Long studentId) {
        return ResponseEntity.ok(submissionService.getStudentSubmissions(studentId));
    }
}

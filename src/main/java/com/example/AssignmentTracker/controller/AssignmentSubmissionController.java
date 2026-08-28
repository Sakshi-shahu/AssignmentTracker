package com.example.AssignmentTracker.controller;

import com.example.AssignmentTracker.Dto.AssignmentSubmissionResponse;
import com.example.AssignmentTracker.service.AssignmentSubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/studentss")
@RequiredArgsConstructor
public class AssignmentSubmissionController {

    private final AssignmentSubmissionService submissionService;

    @PostMapping(value = "/{studentId}/assignments/{assignmentId}/submission"
            , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AssignmentSubmissionResponse> submitAssignment(
            @PathVariable Long studentId,
            @PathVariable Long assignmentId,
            @RequestPart("file") MultipartFile file,
            @RequestHeader("Idempotency-Key") String idempotencyKey) {
        return ResponseEntity.ok(submissionService.submitAssignmentFile(studentId, assignmentId, file, idempotencyKey));
    }

    @PutMapping(value = "/{studentId}/assignments/{assignmentId}/submission",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AssignmentSubmissionResponse> updateSubmission(
            @PathVariable Long studentId,
            @PathVariable Long assignmentId,
            @RequestPart("file") MultipartFile file,
            @RequestHeader("Idempotency-Key") String idempotencyKey) {
        return ResponseEntity.ok(submissionService.updateSubmissionFile(studentId, assignmentId, file, idempotencyKey));
    }


    @GetMapping("/{studentId}/assignments")
    public ResponseEntity<List<AssignmentSubmissionResponse>> getStudentSubmissions(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(submissionService.getStudentSubmissions(studentId));
    }


    @GetMapping("/{studentId}/assignments/{assignmentId}")
    public ResponseEntity<AssignmentSubmissionResponse> getSubmission(
            @PathVariable Long studentId,
            @PathVariable Long assignmentId) {
        return ResponseEntity.ok(submissionService.getSubmission(studentId, assignmentId));
    }





}

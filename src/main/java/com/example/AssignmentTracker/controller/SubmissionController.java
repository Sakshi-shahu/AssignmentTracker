package com.example.AssignmentTracker.controller;

import com.example.AssignmentTracker.Exception.JsonProcessingException;
import com.example.AssignmentTracker.entity.AssignmentSubmission;
import com.example.AssignmentTracker.entity.SubmissionStatus;
import com.example.AssignmentTracker.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/student/submission")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;


    //  Student submits assignment
    @PostMapping(value = "/submit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AssignmentSubmission> submitAssignment(
            @RequestPart("submission") String submissionJson,
            @RequestParam("assignment_id") Long assignmentId,
            @RequestParam("student_id") Long studentId,
            @RequestPart("file") MultipartFile file) throws JsonProcessingException {

        System.out.println("===== SUBMIT CONTROLLER HIT =====");

        ObjectMapper objectMapper = new ObjectMapper();
        AssignmentSubmission submission = objectMapper.readValue(submissionJson, AssignmentSubmission.class);
        AssignmentSubmission savedSubmission = submissionService.assignmentSubmission(submission, assignmentId, studentId, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSubmission);
    }


    // 2. Student updates submission
    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AssignmentSubmission>
    updateSubmission(@PathVariable Long id, @RequestPart("file") MultipartFile file) {

        AssignmentSubmission updatedSubmission = submissionService.updateStudentSubmission(id, file);

        return ResponseEntity.ok(updatedSubmission);
    }


    // 3. Student views submission status
    @GetMapping("/{id}/status")
    public ResponseEntity<SubmissionStatus>
    getSubmissionStatus(@PathVariable Long id) {

        AssignmentSubmission submission = submissionService.getSubmission(id);
        return ResponseEntity.ok(submission.getStatus());
    }


    // 4. Student views marks + feedback
    @GetMapping("/{id}/result")
    public ResponseEntity<AssignmentSubmission> getResult(@PathVariable Long id) {

        AssignmentSubmission submission = submissionService.getSubmission(id);
        return ResponseEntity.ok(submission);
    }
}
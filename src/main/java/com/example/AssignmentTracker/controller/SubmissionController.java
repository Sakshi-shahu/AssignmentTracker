package com.example.AssignmentTracker.controller;

import com.example.AssignmentTracker.Exception.JsonProcessingException;
import com.example.AssignmentTracker.entity.AssignmentSubmission;
import com.example.AssignmentTracker.entity.Student;
import com.example.AssignmentTracker.service.SubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequestMapping("/submission")
@RequiredArgsConstructor
public class SubmissionController {

    private  final SubmissionService submissionService;
    @PostMapping(value = "/submit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AssignmentSubmission> addSubmission(
            @RequestPart("submission") String submissionJson,
            @RequestParam("assignment_id") Long assignmentId,
            @RequestParam("student_id") Long studentId,
            @RequestPart("file") MultipartFile file
    ) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        AssignmentSubmission submission = objectMapper.readValue(submissionJson, AssignmentSubmission.class);
        AssignmentSubmission savedSubmission = submissionService.assignmentSubmission(submission, assignmentId, studentId, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSubmission);
    }
    @GetMapping("/getdata/{id}")
    public ResponseEntity<AssignmentSubmission> get(@PathVariable Long id){
         AssignmentSubmission submission=submissionService.getSubmission(id);
         return  ResponseEntity.ok(submission);
    }

    @GetMapping("/getAllData")
    public ResponseEntity<List<AssignmentSubmission>> getAll(){
        List<AssignmentSubmission> submission=submissionService.getAllSubmission();
        return  ResponseEntity.ok(submission);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSubmission(@PathVariable long id){
      submissionService.deleteSubmission(id);
        return  ResponseEntity.ok("AssignmentData delete successfully");

    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllSubmission(){
        submissionService.deleteAllSubmission();
        return  ResponseEntity.ok("delete all submitted Data Successfully");
    }

    @PutMapping("/updates/{id}")
    public ResponseEntity<AssignmentSubmission> updateSubmission(@PathVariable Long id, @RequestBody AssignmentSubmission submission) {
        AssignmentSubmission updatedSubmission = submissionService.update(submission, id);
        return ResponseEntity.ok(updatedSubmission);
    }

}

//package com.example.AssignmentTracker.controller;
//
//import com.example.AssignmentTracker.dto.SubmissionStatusResponse;
//import com.example.AssignmentTracker.Exception.JsonProcessingException;
//import com.example.AssignmentTracker.entity.*;
//import com.example.AssignmentTracker.service.IdempotencyService;
//import com.example.AssignmentTracker.service.StudentAssignmentService;
//import com.example.AssignmentTracker.service.SubmissionServiceImpl;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import tools.jackson.databind.ObjectMapper;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequestMapping("/student/submission")
//@RequiredArgsConstructor
//public class SubmissionController {
//
//    private final SubmissionServiceImpl submissionService;
//    private final StudentAssignmentService assignmentService;
//    private final IdempotencyService idempotencyService;
//
//
//    @PostMapping(value = "/submit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<AssignmentSubmission> submitAssignment(
//            @RequestPart("submission") String submissionJson,
//            @RequestParam("assignment_id") Long assignmentId,
//            @RequestParam("student_id") Long studentId,
//            @RequestPart("file") MultipartFile file) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        AssignmentSubmission submission = objectMapper.readValue(submissionJson, AssignmentSubmission.class);
//        AssignmentSubmission savedSubmission = submissionService.assignmentSubmission(submission, assignmentId, studentId, file);
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedSubmission);
//    }
//
//
//
//
//
//    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<AssignmentSubmission>
//    updateSubmission(@PathVariable Long id, @RequestPart("file") MultipartFile file) {
//        AssignmentSubmission updatedSubmission = submissionService.updateStudentSubmission(id, file);
//        return ResponseEntity.ok(updatedSubmission);
//    }
//
//
//
//    @GetMapping("/{id}/result")
//    public ResponseEntity<AssignmentSubmission> getResult(@PathVariable Long id) {
//        AssignmentSubmission submission = submissionService.getSubmission(id);
//        return ResponseEntity.ok(submission);
//    }
//
//
//
//    @PutMapping("/updates/{id}")
//    public ResponseEntity<AssignmentSubmission> updateTeacherSubmission(@PathVariable Long id, @RequestBody AssignmentSubmission submission) {
//        AssignmentSubmission updatedSubmission = submissionService.update(submission, id);
//        return ResponseEntity.ok(updatedSubmission);
//    }
//
//
//
////kashish part
//    @PostMapping("/{assignmentId}/assign")
//    public ResponseEntity<Assignment> assignAssignment(@PathVariable Long assignmentId) {
//        Assignment assignment =assignmentService.assignAssignment(assignmentId);
//        return ResponseEntity.ok(assignment);
//    }
//
//
//
//
//
//
//    @GetMapping("/student/{studentId}/status")
//    public ResponseEntity<List<SubmissionStatusResponse>> getAssignmentSubmissionStatus(@PathVariable Long studentId) {
//
//        List<AssignmentSubmission> submissions = submissionService.getMySubmissions(studentId);
//        List<SubmissionStatusResponse> responseList = new ArrayList<>();
//
//        for (AssignmentSubmission submission : submissions) {
//            SubmissionStatusResponse response = new SubmissionStatusResponse();
//            response.setStudentName(submission.getStudent().getName());
//            response.setAssignmentTitle(submission.getAssignment().getTitle());
//
//            Teacher teacher = submission.getAssignment().getTeacher();
//            response.setTeacherName(teacher.getName());
//            response.setSubjectTeacher(teacher.getSubject());
//            response.setStatus(submission.getStatus().name());
//            responseList.add(response);
//        }
//
//        return ResponseEntity.ok(responseList);
//    }
//
//
//    @GetMapping("/student/status")
//    public ResponseEntity<List<SubmissionStatusResponse>> getAssignmentSubmissionStatus() {
//
//        List<AssignmentSubmission> submissions = submissionService.getAllSubmissions();
//        List<SubmissionStatusResponse> responseList = new ArrayList<>();
//
//        for (AssignmentSubmission submission : submissions) {
//            SubmissionStatusResponse response = new SubmissionStatusResponse();
//            response.setStudentName(submission.getStudent().getName());
//            response.setAssignmentTitle(submission.getAssignment().getTitle());
//            Teacher teacher = submission.getAssignment().getTeacher();
//            response.setTeacherName(teacher.getName());
//            response.setSubjectTeacher(teacher.getSubject());
//            response.setStatus(submission.getStatus().name());
//            responseList.add(response);
//        }
//        return ResponseEntity.ok(responseList);
//    }
//
//
//
//}
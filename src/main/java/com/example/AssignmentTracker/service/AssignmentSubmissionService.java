package com.example.AssignmentTracker.service;

import com.example.AssignmentTracker.Dto.AssignmentSubmissionResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface AssignmentSubmissionService {
    AssignmentSubmissionResponse submitAssignmentFile(Long studentId, Long assignmentId, MultipartFile file,String idempotencyKey);
    AssignmentSubmissionResponse updateSubmissionFile(Long studentId, Long assignmentId, MultipartFile file,String idempotencyKey);
    List<AssignmentSubmissionResponse> getStudentSubmissions(Long studentId);
    AssignmentSubmissionResponse getSubmission(Long studentId, Long assignmentId);
}

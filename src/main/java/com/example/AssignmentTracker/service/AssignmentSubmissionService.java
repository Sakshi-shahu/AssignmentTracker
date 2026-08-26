package com.example.AssignmentTracker.service;

import com.example.AssignmentTracker.Dto.AssignmentSubmissionRequest;
import com.example.AssignmentTracker.Dto.AssignmentSubmissionResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AssignmentSubmissionService {

    AssignmentSubmissionResponse submitAssignment(Long studentId, Long assignmentId, AssignmentSubmissionRequest request);

    AssignmentSubmissionResponse updateSubmission(Long studentId, Long assignmentId, AssignmentSubmissionRequest request);

    List<AssignmentSubmissionResponse> getStudentSubmissions(Long studentId);

    AssignmentSubmissionResponse getSubmission(Long studentId, Long assignmentId);
}

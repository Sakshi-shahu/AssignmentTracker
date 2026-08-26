package com.example.AssignmentTracker.service.impl;

import com.example.AssignmentTracker.Dto.AssignmentSubmissionRequest;
import com.example.AssignmentTracker.Dto.AssignmentSubmissionResponse;
import com.example.AssignmentTracker.entity.Assignment;
import com.example.AssignmentTracker.entity.AssignmentSubmission;
import com.example.AssignmentTracker.entity.Student;
import com.example.AssignmentTracker.entity.SubmissionStatus;
import com.example.AssignmentTracker.Exception.ResourceNotFoundException;
import com.example.AssignmentTracker.repository.AssignmentRepository;
import com.example.AssignmentTracker.repository.AssignmentSubmissionRepository;
import com.example.AssignmentTracker.repository.StudentRepository;
import com.example.AssignmentTracker.service.AssignmentSubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssignmentSubmissionServiceImpl implements AssignmentSubmissionService {

    private final AssignmentSubmissionRepository submissionRepository;
    private final AssignmentRepository assignmentRepository;
    private final StudentRepository studentRepository;

    @Override
    public AssignmentSubmissionResponse submitAssignment(Long studentId, Long assignmentId, AssignmentSubmissionRequest request) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found"));

        AssignmentSubmission submission = new AssignmentSubmission();
        submission.setStudent(student);
        submission.setAssignment(assignment);
        submission.setSubmissionDate(LocalDateTime.now());
        submission.setSubmissionFile(request.getSubmissionFile());
        submission.setStatus(SubmissionStatus.SUBMITTED);

        submissionRepository.save(submission);
        return mapToResponse(submission);
    }

    @Override
    public AssignmentSubmissionResponse updateSubmission(Long studentId, Long assignmentId, AssignmentSubmissionRequest request) {
        AssignmentSubmission submission = submissionRepository.findByStudentIdAndAssignmentId(studentId, assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Submission not found"));

        if (submission.getStatus() == SubmissionStatus.EVALUATED) {
            throw new RuntimeException("Submission cannot be updated after evaluation");
        }

        submission.setSubmissionFile(request.getSubmissionFile());
        submission.setSubmissionDate(LocalDateTime.now());
        submissionRepository.save(submission);

        return mapToResponse(submission);
    }

    @Override
    public List<AssignmentSubmissionResponse> getStudentSubmissions(Long studentId) {
        return submissionRepository.findByStudentId(studentId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AssignmentSubmissionResponse getSubmission(Long studentId, Long assignmentId) {
        AssignmentSubmission submission = submissionRepository.findByStudentIdAndAssignmentId(studentId, assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Submission not found"));
        return mapToResponse(submission);
    }

    private AssignmentSubmissionResponse mapToResponse(AssignmentSubmission submission) {
        AssignmentSubmissionResponse response = new AssignmentSubmissionResponse();
        response.setId(submission.getId());
        response.setAssignmentId(submission.getAssignment().getId());
        response.setStudentId(submission.getStudent().getId());
        response.setSubmissionDate(submission.getSubmissionDate());
        response.setSubmissionFile(submission.getSubmissionFile());
        response.setStatus(submission.getStatus().name());
        response.setMarks(submission.getMarks());
        response.setFeedback(submission.getFeedback());
        response.setEvaluatedAt(submission.getEvaluatedAt());
        return response;
    }
}

package com.example.AssignmentTracker.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AssignmentSubmissionResponse {
    private Long id;
    private Long assignmentId;
    private Long studentId;
    private LocalDateTime submissionDate;
    private String submissionFile;
    private String status;
    private Double marks;
    private String feedback;
    private LocalDateTime evaluatedAt;
}

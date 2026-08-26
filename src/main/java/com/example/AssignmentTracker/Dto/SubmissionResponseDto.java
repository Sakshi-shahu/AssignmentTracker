package com.example.AssignmentTracker.dto;

import com.example.AssignmentTracker.entity.SubmissionStatus;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class SubmissionResponseDto {

    private Long id;

    private Long assignmentId;

    private String assignmentTitle;

    private Long studentId;

    private String studentName;

    private LocalDateTime submissionDate;

    private String submissionFile;

    private SubmissionStatus status;

    private Double marks;

    private String feedback;

    private LocalDateTime evaluatedAt;
}

package com.example.AssignmentTracker.service;

import com.example.AssignmentTracker.dto.EvaluateSubmissionRequestDto;
import com.example.AssignmentTracker.dto.SubmissionResponseDto;

import java.util.List;

public interface SubmissionService {



    List<SubmissionResponseDto> getTeacherSubmissions(
            Long teacherId
    );

    SubmissionResponseDto getSubmission(
            Long submissionId,
            Long teacherId
    );

    SubmissionResponseDto evaluateSubmission(
            Long submissionId,
            EvaluateSubmissionRequestDto request,
            Long teacherId
    );
}

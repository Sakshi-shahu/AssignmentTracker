package com.example.AssignmentTracker.service;

import com.example.AssignmentTracker.dto.AssignmentRequestDto;
import com.example.AssignmentTracker.dto.AssignmentResponseDto;

import java.util.List;

public interface AssignmentService {

    AssignmentResponseDto createAssignment(
            AssignmentRequestDto request,
            Long teacherId
    );

    AssignmentResponseDto getAssignment(
            Long assignmentId
    );

    List<AssignmentResponseDto> getTeacherAssignments(
            Long teacherId
    );

    AssignmentResponseDto updateAssignment(
            Long assignmentId,
            AssignmentRequestDto request,
            Long teacherId
    );

    void deleteAssignment(
            Long assignmentId,
            Long teacherId
    );

}

package com.example.AssignmentTracker.service;

import com.example.AssignmentTracker.dto.AssignmentStudentResponseDto;

import java.util.List;

public interface AssignmentStudentService {

    List<AssignmentStudentResponseDto> assignStudents(
            Long assignmentId,
            List<Long> studentIds,
            Long teacherId
    );

    List<AssignmentStudentResponseDto> getAssignedStudents(
            Long assignmentId,
            Long teacherId
    );

    void removeStudent(
            Long assignmentStudentId,
            Long teacherId
    );
}
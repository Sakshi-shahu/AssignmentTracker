package com.example.AssignmentTracker.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AssignmentStudentResponseDto {


    private Long id;

    private Long assignmentId;

    private String assignmentTitle;

    private Long studentId;

    private String studentName;

    private String studentEmail;

    private LocalDateTime assignedAt;


}

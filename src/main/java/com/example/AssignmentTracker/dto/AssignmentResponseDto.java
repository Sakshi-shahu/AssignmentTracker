package com.example.AssignmentTracker.dto;

import com.example.AssignmentTracker.entity.AssignmentStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AssignmentResponseDto {


    private Long id;

    private String title;

    private String description;

    private String course;

    private LocalDate assignedDate;

    private LocalDate dueDate;

    private Integer maxMarks;

    private AssignmentStatus status;

    private Long teacherId;

    private String teacherName;
}

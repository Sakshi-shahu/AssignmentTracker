package com.example.AssignmentTracker.dto;

import com.example.AssignmentTracker.entity.Teacher;

import java.time.LocalDate;

public record AssignmentResponseDto(String title,
                                    String description,
                                    LocalDate assignedDate,
                LocalDate dueDate,
                Teacher teacher
) {
}

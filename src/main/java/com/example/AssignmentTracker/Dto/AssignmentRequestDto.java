package com.example.AssignmentTracker.dto;

import com.example.AssignmentTracker.entity.AssignmentStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AssignmentRequestDto {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100)
    private String title;

    @NotBlank(message = "Description is required")
    @Size(max = 500)
    private String description;

    @NotBlank(message = "Course is required")
    private String course;

    @NotNull(message = "Assigned date is required")
    private LocalDate assignedDate;

    @NotNull(message = "Due date is required")
    private LocalDate dueDate;

    @NotNull(message = "Maximum marks are required")
    @Min(1)
    private Integer maxMarks;

    @NotNull(message = "Status is required")
    private AssignmentStatus status;

}

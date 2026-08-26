package com.example.AssignmentTracker.dto;

import com.example.AssignmentTracker.entity.SubmissionStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EvaluateSubmissionRequestDto {


    @NotNull(message = "Marks are required")
    @Min(value = 0, message = "Marks cannot be negative")
    private Double marks;

    private String feedback;

    @NotNull(message = "Status is required")
    private SubmissionStatus status;

}

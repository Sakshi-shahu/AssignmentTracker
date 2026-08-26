package com.example.AssignmentTracker.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class AssignmentStudentRequestDto {

    @NotEmpty(message = "Select at least one student")
    private List<Long> studentIds;

}

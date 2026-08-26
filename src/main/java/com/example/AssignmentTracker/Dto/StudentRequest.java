package com.example.AssignmentTracker.Dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class StudentRequest {
    private String name;
    private String email;
    private String course;
    private String password;
}


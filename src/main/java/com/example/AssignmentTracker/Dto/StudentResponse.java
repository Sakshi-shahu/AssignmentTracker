package com.example.AssignmentTracker.Dto;

import lombok.Data;

@Data
public class StudentResponse {
    private Long id;
    private String name;
    private String email;
    private String course;
    private boolean active;
}

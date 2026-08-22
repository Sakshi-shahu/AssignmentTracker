package com.example.AssignmentTracker.dto;

import com.example.AssignmentTracker.entity.Teacher;

public record StudentResponseDto(String name, String email,
                                 String course,
                                 Teacher teacher) {
}

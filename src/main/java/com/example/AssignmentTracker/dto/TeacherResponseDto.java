package com.example.AssignmentTracker.dto;

import com.example.AssignmentTracker.entity.Student;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import java.util.List;

public record TeacherResponseDto(
        String name,

 List<Student> studentList,

String subject) {
}

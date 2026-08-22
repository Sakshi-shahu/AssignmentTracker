package com.example.AssignmentTracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    String title;

    @NotEmpty(message = "description is required")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    String description;

    @NotNull(message = "Assigned date is required")
    LocalDate assignedDate;

    @NotNull(message = "DueDate is required")
    LocalDate dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "Teacher is required")
    @JoinColumn(name = "teacher_id")
    Teacher teacher;

    @ManyToMany
    @JoinTable(
            name = "assignment_student",
            joinColumns = @JoinColumn(name = "assignment_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> students = new ArrayList<>();
}
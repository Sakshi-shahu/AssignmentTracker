package com.example.AssignmentTracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@ToString
@JsonPropertyOrder({"title", "description", "course", "assignedDate", "dueDate", "teacher", "id"})
public class Assignment{

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @NotEmpty(message = "description is required")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Assigned date is required")
    private LocalDate assignedDate;

    @NotNull(message = "DueDate is required")
    private LocalDate dueDate;



    @NotNull
    @Min(1)
    private Integer maxMarks;

    @Enumerated(EnumType.STRING)
    private AssignmentStatus status;

    private String course;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "Teacher is required")
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin createdBy;


}
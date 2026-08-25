package com.example.AssignmentTracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Data
@ToString
public class AssignmentSubmission {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "Assignment is required")
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "Student is required")
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @NotNull(message = "Date is required")
    private LocalDateTime submissionDate;

    @NotBlank(message = "Submit file before due date")
    private String submissionFile;

    @Enumerated(EnumType.STRING)
    private SubmissionStatus status;

    @Min(0)
    private Double marks;

    private String feedback;

    private LocalDateTime evaluatedAt;
}
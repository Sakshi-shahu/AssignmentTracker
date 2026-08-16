package com.example.AssignmentTracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Data
@ToString
@Table(name = "assignment_submission")
public class AssignmentSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull(message = "Assignment is required")
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;

    @ManyToOne
    @NotNull(message = "Student is required")
    @JoinColumn(name = "student_id")
    private Student student;

    @NotNull(message = "Date is required")
    private LocalDateTime submissionDate;

    @NotBlank(message = "Submit file before due date")
    private String submissionFile;

    @Enumerated(EnumType.STRING)
    private SubmissionStatus status;

    private Integer marks;

    private String feedback;
}
package com.example.AssignmentTracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Data
@ToString
public class AssignmentSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

    @ManyToOne
    @JoinColumn(name="assignment_id")
  private Assignment assignment;
    @ManyToOne
    @JoinColumn(name = "student_id")
   private Student student;
    @NonNull
   private LocalDateTime submissionDate;
    @NotBlank
  private   String submissionFile;
    @Enumerated(EnumType.STRING)
  private  SubmissionStatus status;
   private  Integer marks;
   private  String feedback;
}

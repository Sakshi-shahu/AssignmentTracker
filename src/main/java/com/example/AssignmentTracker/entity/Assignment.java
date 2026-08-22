package com.example.AssignmentTracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Data
@ToString
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
  private   String title;
    @NotEmpty(message = "description is required")
    @Size(max=500,message ="Description cannot exceed 500 characters" )
    private   String description;
    @NotNull(message = "Assigned date is required")
    private  LocalDate assignedDate;
    @NotNull(message = "DueDate is required")
    private  LocalDate dueDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "Teacher is required")
    @JoinColumn(name="teacher_id")
    private  Teacher teacher;

    // done my kashish
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin createdBy;
}

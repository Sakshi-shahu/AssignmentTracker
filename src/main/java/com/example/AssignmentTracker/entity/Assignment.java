package com.example.AssignmentTracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Data
@ToString
public class Assignment {
    @Id
    Long id;
    String title;
    String description;
    LocalDate assignedDate;
    LocalDate dueDate;
    Long teacherId;
}

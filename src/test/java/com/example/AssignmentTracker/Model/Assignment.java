package com.example.AssignmentTracker.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Data
@ToString
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String title;
    String description;
    @OneToMany(fetch = FetchType.LAZY)
    String assigned_to;
    @OneToMany(fetch = FetchType.LAZY)
    String assigned_by;
    LocalDateTime date;
    Integer maximum_marks;
    String assignment_status;
    LocalDateTime assigned_date;
}

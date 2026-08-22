package com.example.AssignmentTracker.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "student")
@Data
@ToString
public class Student {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @NotBlank
 @Column(nullable = false)
 private String name;

 @NotBlank
 @Email
 @Column(nullable = false, unique = true)
 private String email;

 @NotBlank
 @Column(nullable = false)
 private String course;

 @ManyToOne
 @JoinColumn(name = "teacher_id")
 @JsonBackReference
 private Teacher teacher;
}
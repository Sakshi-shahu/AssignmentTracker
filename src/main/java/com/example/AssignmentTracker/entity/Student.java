package com.example.AssignmentTracker.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
public class Student {
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotBlank
    String name;
    @NotBlank
    @Email
    String email;
    @NotBlank
    String course;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    @JsonBackReference
    private  Teacher teacher;
}

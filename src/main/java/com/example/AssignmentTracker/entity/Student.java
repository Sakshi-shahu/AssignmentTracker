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
    private   Long id;
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private  String email;
    @NotBlank
    private   String course;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    @JsonBackReference
    private  Teacher teacher;


    // done by kashish
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin createdByAdmin;


    // done by kashish
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "super_admin_id")
    private SuperAdmin createdBySuperAdmin;
}

package com.example.AssignmentTracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@ToString
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String subject;

    // Teacher is created by a SuperAdmin
    @ManyToOne
    @JoinColumn(name = "admin_id")
    @JsonIgnoreProperties("teachers")
    private Admin createdByAdmin;

    // Teacher has many students
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Student> studentList;


}
package com.example.AssignmentTracker.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "super_admin_id")
    private SuperAdmin createdBy;

    @OneToMany(mappedBy = "createdByAdmin")
    private List<Teacher> teachers;

    @OneToMany(mappedBy = "createdByAdmin")
    private List<Student> students;

    @OneToMany(mappedBy = "createdBy")
    private List<Assignment> assignments;
}
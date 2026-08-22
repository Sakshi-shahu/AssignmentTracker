package com.example.AssignmentTracker.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "super_admin")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SuperAdmin {

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

    @OneToMany(mappedBy = "createdBy")
    private List<Admin> admins;

    @OneToMany(mappedBy = "createdBySuperAdmin")
    private List<Teacher> teachers;

    @OneToMany(mappedBy = "createdBySuperAdmin")
    private List<Student> students;
}
package com.example.AssignmentTracker.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    @ManyToOne(fetch = FetchType.LAZY)
    Student studentList;

    String subject;

    // SuperAdmin who created this teacher
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "super_admin_id")
//    SuperAdmin createdBySuperAdmin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin createdByAdmin;
}
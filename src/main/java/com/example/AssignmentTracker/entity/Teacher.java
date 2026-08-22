package com.example.AssignmentTracker.entity;

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
    private  Long id;
    private   String name;
    @OneToMany(mappedBy = "teacher",cascade = CascadeType.ALL)
    private List<Student> studentList;

    private    String subject;

// done by kashish
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin createdByAdmin;

//done by kashish
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "super_admin_id")
    private SuperAdmin createdBySuperAdmin;

}
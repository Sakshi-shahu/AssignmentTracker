package com.example.AssignmentTracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
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


    private boolean active = true;

    // SuperAdmin creates Admins
    @OneToMany(mappedBy = "createdBy")
    //@JsonManagedReference
    @JsonIgnoreProperties("createdBy")
    private List<Admin> admins;

    // SuperAdmin creates Teachers
    //@OneToMany(mappedBy = "createdBySuperAdmin")
//    @OneToMany(mappedBy = "createdByAdmin") // 👈 mappedBy property ko Teacher entity ke actual field se match karein
//    @JsonIgnoreProperties("createdByAdmin")
//    private List<Teacher> teachers;
}
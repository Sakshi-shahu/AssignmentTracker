package com.example.AssignmentTracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "admin")
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@NoArgsConstructor
@AllArgsConstructor
@Data
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

    private Boolean active = true;

    @ManyToOne
    @JoinColumn(name = "super_admin_id")
    @JsonIgnoreProperties("admins")
    private SuperAdmin createdBy;
}
package com.example.AssignmentTracker.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
  private   Long id;
  @NotBlank(message = "name cannot be blank")
 private    String name;
    @Column(nullable = false, unique = true)
    @Email(message = "enter proper email ")
    private String email;

    @NotBlank @Size(min = 10, max = 10)
    private  String phone;


    @Column(nullable = false)
    private String password;

    private boolean active = true;
    @NotBlank(message = "subject cannot be blank")
   private String subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin createdByAdmin;
}
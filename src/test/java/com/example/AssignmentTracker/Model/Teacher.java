package com.example.AssignmentTracker.Model;


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
    Integer id;
    String name;
    @OneToMany(fetch = FetchType.LAZY)
    List<Student> studentList;

    String subject;

}

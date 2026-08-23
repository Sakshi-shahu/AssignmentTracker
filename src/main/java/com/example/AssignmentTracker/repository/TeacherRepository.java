package com.example.AssignmentTracker.repository;

import com.example.AssignmentTracker.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}

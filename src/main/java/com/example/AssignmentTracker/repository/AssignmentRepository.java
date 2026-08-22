package com.example.AssignmentTracker.repository;

import com.example.AssignmentTracker.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment,Long> {
    List<Assignment> findByStudents_Id(Long studentId);

}

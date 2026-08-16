package com.example.AssignmentTracker.repository;

import com.example.AssignmentTracker.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment,Long> {
}

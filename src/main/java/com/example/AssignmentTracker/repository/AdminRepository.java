package com.example.AssignmentTracker.repository;

import com.example.AssignmentTracker.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {
}

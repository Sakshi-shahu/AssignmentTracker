package com.example.AssignmentTracker.repository;

import com.example.AssignmentTracker.Dto.RequestDto;
import com.example.AssignmentTracker.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
}

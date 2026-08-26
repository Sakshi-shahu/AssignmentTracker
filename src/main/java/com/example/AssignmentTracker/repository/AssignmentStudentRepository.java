package com.example.AssignmentTracker.repository;

import com.example.AssignmentTracker.entity.AssignmentStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentStudentRepository  extends JpaRepository<AssignmentStudent, Long> {


    boolean existsByAssignmentIdAndStudentId(
            Long assignmentId,
            Long studentId
    );

    List<AssignmentStudent> findByAssignmentId(
            Long assignmentId
    );

    List<AssignmentStudent> findByStudentId(
            Long studentId
    );
}

package com.example.AssignmentTracker.repository;

import com.example.AssignmentTracker.entity.Assignment;
import com.example.AssignmentTracker.entity.AssignmentSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AssignmentRepository extends JpaRepository<Assignment,Long> {
    List<Assignment> findByCourse(String course);

//
//    List<Assignment> findByStudents_Id(Long studentId);
//
//
//    Optional<Assignment> findByIdAndStudents_Id(Long assignmentId, Long studentId);

    java.util.List<Assignment> findByTeacherId(Long teacherId);








}

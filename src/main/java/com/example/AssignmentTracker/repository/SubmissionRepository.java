package com.example.AssignmentTracker.repository;

import com.example.AssignmentTracker.entity.AssignmentSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<AssignmentSubmission,Long> {
    List<AssignmentSubmission> findByAssignment_Teacher_Id(Long teacherId);


}

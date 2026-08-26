package com.example.AssignmentTracker.repository;

import com.example.AssignmentTracker.entity.AssignmentSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<AssignmentSubmission,Long> {
    List<AssignmentSubmission> findByAssignmentTeacherId(Long teacherId);// now used by kashish



    // Get submission for a given assignment and student
    Optional<AssignmentSubmission> findByAssignment_IdAndStudent_Id(Long assignmentId, Long studentId);

    List<AssignmentSubmission> findByStudentId(Long studentId);



    List<AssignmentSubmission>
    findByAssignmentId(Long assignmentId); // use by kashish

}

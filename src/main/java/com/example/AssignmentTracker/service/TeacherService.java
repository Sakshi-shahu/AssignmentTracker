package com.example.AssignmentTracker.service;


import com.example.AssignmentTracker.entity.Assignment;
import com.example.AssignmentTracker.entity.AssignmentSubmission;
import com.example.AssignmentTracker.entity.Student;


import java.util.List;


public interface TeacherService {


    Assignment createAssignment(Long teacherId, Assignment assignment);

    List<Assignment> getAssignmentsByTeacher(Long teacherId);

    Assignment updateAssignment(Long assignmentId, Assignment assignment);

    void assignAssignmentToStudent(Long assignmentId, Long studentId);


    List<Student> getStudentsByTeacher(Long teacherId);

    List<AssignmentSubmission> getSubmissionsForTeacher(Long teacherId);

    AssignmentSubmission evaluateSubmission(Long submissionId, double marks, String feedback, String status);



}

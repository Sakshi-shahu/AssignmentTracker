package com.example.AssignmentTracker.service;

import com.example.AssignmentTracker.Exception.TeacherNotFoundException;
import com.example.AssignmentTracker.dto.TeacherResponseDto;
import com.example.AssignmentTracker.entity.Assignment;
import com.example.AssignmentTracker.entity.AssignmentSubmission;
import com.example.AssignmentTracker.entity.Student;
import com.example.AssignmentTracker.entity.Teacher;
import com.example.AssignmentTracker.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TeacherService {


    Assignment createAssignment(Long teacherId, Assignment assignment);

    List<Assignment> getAssignmentsByTeacher(Long teacherId);

    Assignment updateAssignment(Long assignmentId, Assignment assignment);

    void assignAssignmentToStudent(Long assignmentId, Long studentId);

    // Student View
    List<Student> getStudentsByTeacher(Long teacherId);

    // Submissions & Evaluation
    List<AssignmentSubmission> getSubmissionsForTeacher(Long teacherId);

    AssignmentSubmission evaluateSubmission(Long submissionId, double marks, String feedback, String status);



}

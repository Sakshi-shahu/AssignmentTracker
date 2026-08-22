package com.example.AssignmentTracker.service;

import com.example.AssignmentTracker.Exception.AssignmentNotFoundException;
import com.example.AssignmentTracker.entity.Assignment;
import com.example.AssignmentTracker.repository.AssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentAssignmentService {

    private final AssignmentRepository assignmentRepository;

    public List<Assignment> getAssignments(Long studentId) {

        return assignmentRepository.findByStudents_Id(studentId);
    }

    public Assignment getAssignment(Long assignmentId) {

        return assignmentRepository.findById(assignmentId)
                .orElseThrow(()->
                        new AssignmentNotFoundException("Assignment not found with id: " + assignmentId));
    }
}
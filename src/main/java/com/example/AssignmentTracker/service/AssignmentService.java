package com.example.AssignmentTracker.service;

import com.example.AssignmentTracker.entity.Assignment;

public interface AssignmentService {

    Assignment assignAssignment(Long assignmentId, Long studentId);

}
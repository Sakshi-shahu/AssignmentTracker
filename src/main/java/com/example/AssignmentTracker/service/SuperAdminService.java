package com.example.AssignmentTracker.service;

import com.example.AssignmentTracker.Dto.AdminRequest;
import com.example.AssignmentTracker.Dto.SuperAdminRequest;
import com.example.AssignmentTracker.Dto.AdminRequest;
import com.example.AssignmentTracker.Dto.AssignmentStatistics;
import com.example.AssignmentTracker.Dto.SuperAdminRequest;
import com.example.AssignmentTracker.entity.*;
import jakarta.validation.Valid;

import java.util.List;

public interface SuperAdminService {

    // ================= ADMIN =================

    Admin createAdmin(AdminRequest request, Long superAdminId);

    List<Admin> getAllAdmins();

    Admin updateAdmin(Long adminId, @Valid AdminRequest request);

    void deactivateAdmin(Long adminId);


    // ================= TRAINER =================

    List<Teacher> getAllTrainers();


    // ================= STUDENT =================

    List<Student> getAllStudents();


    // ================= ASSIGNMENT =================

    List<Assignment> getAllAssignments();


    // ================= SUBMISSIONS =================

    List<AssignmentSubmission> getAllSubmissions();


    // ================= STATISTICS =================

    AssignmentStatistics getAssignmentStatistics();


    SuperAdmin createSuperAdmin(@Valid SuperAdminRequest request);
}
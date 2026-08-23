package com.example.AssignmentTracker.service;

import com.example.AssignmentTracker.dto.AdminRequest;
import com.example.AssignmentTracker.dto.AssignmentStatistics;
import com.example.AssignmentTracker.dto.SuperAdminRequest;
import com.example.AssignmentTracker.entity.*;

import java.util.List;

public interface SuperAdminService {

    // ================= ADMIN =================

    Admin createAdmin(AdminRequest request, Long superAdminId);

    List<Admin> getAllAdmins();

    Admin updateAdmin(Long adminId, AdminRequest request);

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


    SuperAdmin createSuperAdmin(SuperAdminRequest request);
}
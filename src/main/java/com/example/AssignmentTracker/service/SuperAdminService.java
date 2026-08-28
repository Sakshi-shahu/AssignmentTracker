package com.example.AssignmentTracker.service;

import com.example.AssignmentTracker.dto.SuperAdminRequestDto;
import com.example.AssignmentTracker.dto.SuperAdminResponseDto;
import com.example.AssignmentTracker.entity.Admin;
import com.example.AssignmentTracker.entity.Assignment;
import com.example.AssignmentTracker.entity.AssignmentSubmission;
import com.example.AssignmentTracker.entity.Student;
import com.example.AssignmentTracker.entity.Teacher;

import java.util.List;

public interface SuperAdminService {

    // ================= SUPER ADMIN =================

    SuperAdminResponseDto createSuperAdmin(
            SuperAdminRequestDto request);

    List<SuperAdminResponseDto> getAllSuperAdmins();

    SuperAdminResponseDto getSuperAdminById(Long id);

    SuperAdminResponseDto updateSuperAdmin(
            Long id,
            SuperAdminRequestDto request);

    void deleteSuperAdmin(Long id);


    // ================= ADMIN MANAGEMENT =================

    Admin createAdmin(Long superAdminId, Admin admin);

    List<Admin> getAllAdmins();

    Admin getAdminById(Long id);

    Admin updateAdmin(Long id, Admin admin);

    void deleteAdmin(Long id);


    // ================= TRAINER MANAGEMENT =================

    List<Teacher> getAllTrainers();

    Teacher getTrainerById(Long id);


    // ================= STUDENT MANAGEMENT =================

    List<Student> getAllStudents();

    Student getStudentById(Long id);


    // ================= ASSIGNMENT MANAGEMENT =================

    List<Assignment> getAllAssignments();

    Assignment getAssignmentById(Long id);


    // ================= SUBMISSION MANAGEMENT =================

    List<AssignmentSubmission> getAllSubmissions();

    List<AssignmentSubmission> getSubmissionsByAssignment(
            Long assignmentId);
}
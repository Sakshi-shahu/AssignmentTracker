package com.example.AssignmentTracker.service;

import com.example.AssignmentTracker.Dto.AdminRequest;
import com.example.AssignmentTracker.Dto.SuperAdminRequest;
import com.example.AssignmentTracker.Dto.AssignmentStatistics;
import com.example.AssignmentTracker.entity.Admin;
import com.example.AssignmentTracker.entity.Assignment;
import com.example.AssignmentTracker.entity.AssignmentSubmission;
import com.example.AssignmentTracker.entity.Student;
import com.example.AssignmentTracker.entity.SuperAdmin;
import com.example.AssignmentTracker.entity.Teacher;
import com.example.AssignmentTracker.repository.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SuperAdminServiceImpl implements SuperAdminService {

    private final SuperAdminRepository superAdminRepository;
    private final AdminRepository adminRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final AssignmentRepository assignmentRepository;
    private final AssignmentSubmissionRepository submissionRepository;


    // ================= ADMIN =================

    @Override
    public Admin createAdmin(
            AdminRequest request,
            Long superAdminId) {

        SuperAdmin superAdmin = superAdminRepository.findById(superAdminId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "SuperAdmin not found with id: " + superAdminId));

        Admin admin = new Admin();

        admin.setName(request.getName());
        admin.setEmail(request.getEmail());
        admin.setPassword(request.getPassword());
        admin.setActive(true);

        admin.setCreatedBy(superAdmin);

        return adminRepository.save(admin);
    }


    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }


    @Override
    public Admin updateAdmin(
            Long adminId,
            @Valid AdminRequest request) {

        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Admin not found with id: " + adminId));

        admin.setName(request.getName());
        admin.setEmail(request.getEmail());
        admin.setPassword(request.getPassword());

        return adminRepository.save(admin);
    }


    @Override
    public void deactivateAdmin(Long adminId) {

        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Admin not found with id: " + adminId));

        admin.setActive(false);

        adminRepository.save(admin);
    }


    // ================= TRAINER =================

    @Override
    public List<Teacher> getAllTrainers() {
        return teacherRepository.findAll();
    }


    // ================= STUDENT =================

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }


    // ================= ASSIGNMENT =================

    @Override
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }


    // ================= SUBMISSIONS =================

    @Override
    public List<AssignmentSubmission> getAllSubmissions() {
        return submissionRepository.findAll();
    }


    // ================= STATISTICS =================

    @Override
    public AssignmentStatistics getAssignmentStatistics() {

        long totalAssignments =
                assignmentRepository.count();

        long totalSubmissions =
                submissionRepository.count();

        long totalAdmins =
                adminRepository.count();

        long totalTrainers =
                teacherRepository.count();

        long totalStudents =
                studentRepository.count();

        return new AssignmentStatistics(
                totalAssignments,
                totalSubmissions,
                totalAdmins,
                totalTrainers,
                totalStudents
        );
    }



    @Override
    public SuperAdmin createSuperAdmin(@Valid SuperAdminRequest request) {

        SuperAdmin superAdmin = new SuperAdmin();

        superAdmin.setName(request.getName());
        superAdmin.setEmail(request.getEmail());
        superAdmin.setPassword(request.getPassword());
        superAdmin.setActive(true);

        return superAdminRepository.save(superAdmin);
    }
}
package com.example.AssignmentTracker.service;

import com.example.AssignmentTracker.dto.SuperAdminRequestDto;
import com.example.AssignmentTracker.dto.SuperAdminResponseDto;
import com.example.AssignmentTracker.Exception.SuperAdminNotFoundException;
import com.example.AssignmentTracker.entity.Admin;
import com.example.AssignmentTracker.entity.Assignment;
import com.example.AssignmentTracker.entity.AssignmentSubmission;
import com.example.AssignmentTracker.entity.Student;
import com.example.AssignmentTracker.entity.SuperAdmin;
import com.example.AssignmentTracker.entity.Teacher;
import com.example.AssignmentTracker.repository.AdminRepository;
import com.example.AssignmentTracker.repository.AssignmentRepository;
import com.example.AssignmentTracker.repository.StudentRepository;
import com.example.AssignmentTracker.repository.SubmissionRepository;
import com.example.AssignmentTracker.repository.SuperAdminRepository;
import com.example.AssignmentTracker.repository.TeacherRepository;
import com.example.AssignmentTracker.service.SuperAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SuperAdminServiceImpl implements SuperAdminService {

    private final SuperAdminRepository superAdminRepository;
    private final AdminRepository adminRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final AssignmentRepository assignmentRepository;
    private final SubmissionRepository submissionRepository;


    // ================= SUPER ADMIN =================

    @Override
    public SuperAdminResponseDto createSuperAdmin(
            SuperAdminRequestDto request) {

        SuperAdmin superAdmin = new SuperAdmin();

        superAdmin.setName(request.getName());
        superAdmin.setEmail(request.getEmail());
        superAdmin.setPassword(request.getPassword());
        superAdmin.setPhone(request.getPhone());

        SuperAdmin saved =
                superAdminRepository.save(superAdmin);

        return convertToResponse(saved);
    }


    @Override
    public List<SuperAdminResponseDto> getAllSuperAdmins() {

        return superAdminRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }


    @Override
    public SuperAdminResponseDto getSuperAdminById(Long id) {

        SuperAdmin superAdmin =
                superAdminRepository.findById(id)
                        .orElseThrow(() ->
                                new SuperAdminNotFoundException(
                                        "Super Admin not found with id: " + id));

        return convertToResponse(superAdmin);
    }


    @Override
    public SuperAdminResponseDto updateSuperAdmin(
            Long id,
            SuperAdminRequestDto request) {

        SuperAdmin superAdmin =
                superAdminRepository.findById(id)
                        .orElseThrow(() ->
                                new SuperAdminNotFoundException(
                                        "Super Admin not found with id: " + id));

        superAdmin.setName(request.getName());
        superAdmin.setEmail(request.getEmail());
        superAdmin.setPassword(request.getPassword());
        superAdmin.setPhone(request.getPhone());

        SuperAdmin updated =
                superAdminRepository.save(superAdmin);

        return convertToResponse(updated);
    }


    @Override
    public void deleteSuperAdmin(Long id) {

        SuperAdmin superAdmin =
                superAdminRepository.findById(id)
                        .orElseThrow(() ->
                                new SuperAdminNotFoundException(
                                        "Super Admin not found with id: " + id));

        superAdminRepository.delete(superAdmin);
    }


    // ================= ADMIN MANAGEMENT =================

    @Override
    public Admin createAdmin(Long superAdminId, Admin admin) {

        SuperAdmin superAdmin =
                superAdminRepository.findById(superAdminId)
                        .orElseThrow(() ->
                                new SuperAdminNotFoundException(
                                        "Super Admin not found with id: "
                                                + superAdminId));

        admin.setCreatedBy(superAdmin);

        return adminRepository.save(admin);
    }


    @Override
    public List<Admin> getAllAdmins() {

        return adminRepository.findAll();
    }


    @Override
    public Admin getAdminById(Long id) {

        return adminRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Admin not found with id: " + id));
    }


    @Override
    public Admin updateAdmin(Long id, Admin admin) {

        Admin existingAdmin =
                adminRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Admin not found with id: " + id));

        existingAdmin.setName(admin.getName());
        existingAdmin.setEmail(admin.getEmail());
        existingAdmin.setPassword(admin.getPassword());
        existingAdmin.setActive(admin.getActive());

        return adminRepository.save(existingAdmin);
    }


    @Override
    public void deleteAdmin(Long id) {

        Admin admin =
                adminRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Admin not found with id: " + id));

        adminRepository.delete(admin);
    }


    // ================= TRAINER MANAGEMENT =================

    @Override
    public List<Teacher> getAllTrainers() {

        return teacherRepository.findAll();
    }


    @Override
    public Teacher getTrainerById(Long id) {

        return teacherRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Trainer not found with id: " + id));
    }


    // ================= STUDENT MANAGEMENT =================

    @Override
    public List<Student> getAllStudents() {

        return studentRepository.findAll();
    }


    @Override
    public Student getStudentById(Long id) {

        return studentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found with id: " + id));
    }


    // ================= ASSIGNMENT MANAGEMENT =================

    @Override
    public List<Assignment> getAllAssignments() {

        return assignmentRepository.findAll();
    }


    @Override
    public Assignment getAssignmentById(Long id) {

        return assignmentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Assignment not found with id: " + id));
    }


    // ================= SUBMISSION MANAGEMENT =================

    @Override
    public List<AssignmentSubmission> getAllSubmissions() {

        return submissionRepository.findAll();
    }


    @Override
    public List<AssignmentSubmission> getSubmissionsByAssignment(
            Long assignmentId) {

        return submissionRepository.findByAssignmentId(assignmentId);
    }


    // ================= dto CONVERTER =================

    private SuperAdminResponseDto convertToResponse(
            SuperAdmin superAdmin) {

        SuperAdminResponseDto response =
                new SuperAdminResponseDto();

        response.setId(superAdmin.getId());
        response.setName(superAdmin.getName());
        response.setEmail(superAdmin.getEmail());
        response.setPhone(superAdmin.getPhone());
        response.setActive(superAdmin.isActive());

        return response;
    }
}
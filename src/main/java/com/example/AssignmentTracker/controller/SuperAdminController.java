package com.example.AssignmentTracker.controller;


import com.example.AssignmentTracker.dto.SuperAdminRequestDto;
import com.example.AssignmentTracker.dto.SuperAdminResponseDto;
import com.example.AssignmentTracker.entity.Admin;
import com.example.AssignmentTracker.entity.Assignment;
import com.example.AssignmentTracker.entity.AssignmentSubmission;
import com.example.AssignmentTracker.entity.Student;
import com.example.AssignmentTracker.entity.Teacher;
import com.example.AssignmentTracker.service.SuperAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/superadmin")
@RequiredArgsConstructor
public class SuperAdminController {

    private final SuperAdminService superAdminService;


    // ================= SUPER ADMIN =================

    @PostMapping
    public ResponseEntity<SuperAdminResponseDto> createSuperAdmin(
            @Valid @RequestBody SuperAdminRequestDto request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(superAdminService.createSuperAdmin(request));
    }


    @GetMapping
    public ResponseEntity<List<SuperAdminResponseDto>> getAllSuperAdmins() {

        return ResponseEntity.ok(
                superAdminService.getAllSuperAdmins());
    }


    @GetMapping("/{id}")
    public ResponseEntity<SuperAdminResponseDto> getSuperAdminById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                superAdminService.getSuperAdminById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<SuperAdminResponseDto> updateSuperAdmin(
            @PathVariable Long id,
            @Valid @RequestBody SuperAdminRequestDto request) {

        return ResponseEntity.ok(
                superAdminService.updateSuperAdmin(id, request));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSuperAdmin(
            @PathVariable Long id) {

        superAdminService.deleteSuperAdmin(id);

        return ResponseEntity.noContent().build();
    }


    // ================= ADMIN MANAGEMENT =================

    @PostMapping("/{superAdminId}/admins")
    public ResponseEntity<Admin> createAdmin(
            @PathVariable Long superAdminId,
            @RequestBody Admin admin) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(superAdminService.createAdmin(
                        superAdminId, admin));
    }


    @GetMapping("/admins")
    public ResponseEntity<List<Admin>> getAllAdmins() {

        return ResponseEntity.ok(
                superAdminService.getAllAdmins());
    }


    @GetMapping("/admins/{id}")
    public ResponseEntity<Admin> getAdminById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                superAdminService.getAdminById(id));
    }


    @PutMapping("/admins/{id}")
    public ResponseEntity<Admin> updateAdmin(
            @PathVariable Long id,
            @RequestBody Admin admin) {

        return ResponseEntity.ok(
                superAdminService.updateAdmin(id, admin));
    }


    @DeleteMapping("/admins/{id}")
    public ResponseEntity<Void> deleteAdmin(
            @PathVariable Long id) {

        superAdminService.deleteAdmin(id);

        return ResponseEntity.noContent().build();
    }


    // ================= TRAINER MANAGEMENT =================

    @GetMapping("/trainers")
    public ResponseEntity<List<Teacher>> getAllTrainers() {

        return ResponseEntity.ok(
                superAdminService.getAllTrainers());
    }


    @GetMapping("/trainers/{id}")
    public ResponseEntity<Teacher> getTrainerById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                superAdminService.getTrainerById(id));
    }


    // ================= STUDENT MANAGEMENT =================

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {

        return ResponseEntity.ok(
                superAdminService.getAllStudents());
    }


    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                superAdminService.getStudentById(id));
    }


    // ================= ASSIGNMENT MANAGEMENT =================

    @GetMapping("/assignments")
    public ResponseEntity<List<Assignment>> getAllAssignments() {

        return ResponseEntity.ok(
                superAdminService.getAllAssignments());
    }


    @GetMapping("/assignments/{id}")
    public ResponseEntity<Assignment> getAssignmentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                superAdminService.getAssignmentById(id));
    }


    // ================= SUBMISSION MANAGEMENT =================

    @GetMapping("/submissions")
    public ResponseEntity<List<AssignmentSubmission>> getAllSubmissions() {

        return ResponseEntity.ok(
                superAdminService.getAllSubmissions());
    }


    @GetMapping("/assignments/{assignmentId}/submissions")
    public ResponseEntity<List<AssignmentSubmission>>
    getSubmissionsByAssignment(
            @PathVariable Long assignmentId) {

        return ResponseEntity.ok(
                superAdminService.getSubmissionsByAssignment(
                        assignmentId));
    }
}
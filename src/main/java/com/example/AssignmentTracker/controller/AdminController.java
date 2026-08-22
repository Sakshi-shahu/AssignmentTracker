package com.example.AssignmentTracker.controller;

import com.example.AssignmentTracker.dto.AdminRequest;
import com.example.AssignmentTracker.entity.Admin;
import com.example.AssignmentTracker.entity.Assignment;
import com.example.AssignmentTracker.entity.AssignmentSubmission;
import com.example.AssignmentTracker.entity.Student;
import com.example.AssignmentTracker.entity.Teacher;
import com.example.AssignmentTracker.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;


    // ================= ADMIN =================

    @PostMapping
    public ResponseEntity<Admin> createAdmin(
            @Valid @RequestBody AdminRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(adminService.createAdmin(request));
    }

    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins() {

        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(
            @PathVariable Long id) {

        return ResponseEntity.ok(adminService.getAdminById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Admin> updateAdmin(
            @PathVariable Long id,
            @Valid @RequestBody AdminRequest request) {

        return ResponseEntity.ok(
                adminService.updateAdmin(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(
            @PathVariable Long id) {

        adminService.deleteAdmin(id);

        return ResponseEntity.noContent().build();
    }


    // ================= TRAINER =================

    @PostMapping("/trainers")
    public ResponseEntity<Teacher> createTrainer(
            @RequestBody Teacher teacher) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(adminService.createTrainer(teacher));
    }

    @GetMapping("/trainers")
    public ResponseEntity<List<Teacher>> getAllTrainers() {

        return ResponseEntity.ok(
                adminService.getAllTrainers());
    }

    @GetMapping("/trainers/{id}")
    public ResponseEntity<Teacher> getTrainerById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                adminService.getTrainerById(id));
    }

    @PutMapping("/trainers/{id}")
    public ResponseEntity<Teacher> updateTrainer(
            @PathVariable Long id,
            @RequestBody Teacher teacher) {

        return ResponseEntity.ok(
                adminService.updateTrainer(id, teacher));
    }

    @DeleteMapping("/trainers/{id}")
    public ResponseEntity<Void> deleteTrainer(
            @PathVariable Long id) {

        adminService.deleteTrainer(id);

        return ResponseEntity.noContent().build();
    }


    // ================= STUDENT =================

    @PostMapping("/students/{teacherId}")
    public ResponseEntity<Student> createStudent(
            @PathVariable Long teacherId,
            @RequestBody Student student) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(adminService.createStudent(student, teacherId));
    }

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {

        return ResponseEntity.ok(
                adminService.getAllStudents());
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                adminService.getStudentById(id));
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @RequestBody Student student) {

        return ResponseEntity.ok(
                adminService.updateStudent(id, student));
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable Long id) {

        adminService.deleteStudent(id);

        return ResponseEntity.noContent().build();
    }


    // ================= ASSIGNMENT =================

    @PostMapping("/assignments/{teacherId}")
    public ResponseEntity<Assignment> createAssignment(
            @PathVariable Long teacherId,
            @RequestBody Assignment assignment) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(adminService.createAssignment(
                        assignment, teacherId));
    }

    @GetMapping("/assignments")
    public ResponseEntity<List<Assignment>> getAllAssignments() {

        return ResponseEntity.ok(
                adminService.getAllAssignments());
    }

    @GetMapping("/assignments/{id}")
    public ResponseEntity<Assignment> getAssignmentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                adminService.getAssignmentById(id));
    }

    @PutMapping("/assignments/{id}")
    public ResponseEntity<Assignment> updateAssignment(
            @PathVariable Long id,
            @RequestBody Assignment assignment) {

        return ResponseEntity.ok(
                adminService.updateAssignment(id, assignment));
    }

    @DeleteMapping("/assignments/{id}")
    public ResponseEntity<Void> deleteAssignment(
            @PathVariable Long id) {

        adminService.deleteAssignment(id);

        return ResponseEntity.noContent().build();
    }


    // ================= SUBMISSIONS =================

    @GetMapping("/submissions")
    public ResponseEntity<List<AssignmentSubmission>> getAllSubmissions() {

        return ResponseEntity.ok(
                adminService.getAllSubmissions());
    }

    @GetMapping("/assignments/{assignmentId}/submissions")
    public ResponseEntity<List<AssignmentSubmission>>
    getSubmissionsByAssignment(
            @PathVariable Long assignmentId) {

        return ResponseEntity.ok(
                adminService.getSubmissionsByAssignment(assignmentId));
    }




    @PostMapping("/assignments/{assignmentId}/students/{studentId}")
    public ResponseEntity<Void> assignAssignmentToStudent(
            @PathVariable Long assignmentId,
            @PathVariable Long studentId) {

        adminService.assignAssignmentToStudent(
                assignmentId,
                studentId);

        return ResponseEntity.ok().build();
    }
}
//package com.example.AssignmentTracker.controller;
//
//import com.example.AssignmentTracker.Dto.AdminRequest;
//import com.example.AssignmentTracker.Dto.AssignmentStatistics;
//import com.example.AssignmentTracker.dto.SuperAdminRequest;
//import com.example.AssignmentTracker.entity.*;
//import com.example.AssignmentTracker.service.SuperAdminService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/superadmin")
//@RequiredArgsConstructor
//public class SuperAdminController {
//
//    private final SuperAdminService superAdminService;
//
//
//    // ================= ADMIN =================
//
//    @PostMapping("/admins/{superAdminId}")
//    public ResponseEntity<Admin> createAdmin(
//            @PathVariable Long superAdminId,
//            @Valid @RequestBody AdminRequest request) {
//
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(superAdminService.createAdmin(
//                        request,
//                        superAdminId));
//    }
//
//
//    @GetMapping("/admins")
//    public ResponseEntity<List<Admin>> getAllAdmins() {
//
//        return ResponseEntity.ok(
//                superAdminService.getAllAdmins());
//    }
//
//
//    @PutMapping("/admins/{adminId}")
//    public ResponseEntity<Admin> updateAdmin(
//            @PathVariable Long adminId,
//            @Valid @RequestBody AdminRequest request) {
//
//        return ResponseEntity.ok(
//                superAdminService.updateAdmin(
//                        adminId,
//                        request));
//    }
//
//
//    @DeleteMapping("/admins/{adminId}")
//    public ResponseEntity<Void> deactivateAdmin(
//            @PathVariable Long adminId) {
//
//        superAdminService.deactivateAdmin(adminId);
//
//        return ResponseEntity.noContent().build();
//    }
//
//
//    // ================= TRAINERS =================
//
//    @GetMapping("/trainers")
//    public ResponseEntity<List<Teacher>> getAllTrainers() {
//
//        return ResponseEntity.ok(
//                superAdminService.getAllTrainers());
//    }
//
//
//    // ================= STUDENTS =================
//
//    @GetMapping("/students")
//    public ResponseEntity<List<Student>> getAllStudents() {
//
//        return ResponseEntity.ok(
//                superAdminService.getAllStudents());
//    }
//
//
//    // ================= ASSIGNMENTS =================
//
//    @GetMapping("/assignments")
//    public ResponseEntity<List<Assignment>> getAllAssignments() {
//
//        return ResponseEntity.ok(
//                superAdminService.getAllAssignments());
//    }
//
//
//    // ================= SUBMISSIONS =================
//
//    @GetMapping("/submissions")
//    public ResponseEntity<List<AssignmentSubmission>> getAllSubmissions() {
//
//        return ResponseEntity.ok(
//                superAdminService.getAllSubmissions());
//    }
//
//
//    // ================= STATISTICS =================
//
//    @GetMapping("/statistics")
//    public ResponseEntity<AssignmentStatistics>
//    getAssignmentStatistics() {
//
//        return ResponseEntity.ok(
//                superAdminService.getAssignmentStatistics());
//    }
//
//
//    @PostMapping("/create")
//    public ResponseEntity<SuperAdmin> createSuperAdmin(
//            @Valid @RequestBody SuperAdminRequest request) {
//
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(superAdminService.createSuperAdmin(request));
//    }
//}
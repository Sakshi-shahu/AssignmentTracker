package com.example.AssignmentTracker.controller;

import com.example.AssignmentTracker.dto.TeacherResponseDto;
import com.example.AssignmentTracker.entity.Assignment;
import com.example.AssignmentTracker.entity.AssignmentSubmission;
import com.example.AssignmentTracker.entity.Student;
import com.example.AssignmentTracker.entity.Teacher;
import com.example.AssignmentTracker.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/t")
@RequiredArgsConstructor
public class TeacherController {


    private  final TeacherService teacherService;

    // Requirement: Create assignments
    @PostMapping("/{teacherId}/assignments")
    public ResponseEntity<Assignment> createAssignment(
            @PathVariable Long teacherId,
            @Valid @RequestBody Assignment assignment) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(teacherService.createAssignment(teacherId, assignment));
    }

    // Requirement: View assigned/created assignments
    @GetMapping("/{teacherId}/assignments")
    public ResponseEntity<List<Assignment>> getAssignmentsByTeacher(@PathVariable Long teacherId) {
        return ResponseEntity.ok(teacherService.getAssignmentsByTeacher(teacherId));
    }

    // Requirement: Update assignments
    @PutMapping("/assignments/{assignmentId}")
    public ResponseEntity<Assignment> updateAssignment(
            @PathVariable Long assignmentId,
            @Valid @RequestBody Assignment assignment) {
        return ResponseEntity.ok(teacherService.updateAssignment(assignmentId, assignment));
    }

    // Requirement: Assign assignments to students
    @PostMapping("/assignments/{assignmentId}/students/{studentId}")
    public ResponseEntity<Void> assignAssignmentToStudent(
            @PathVariable Long assignmentId,
            @PathVariable Long studentId) {
        teacherService.assignAssignmentToStudent(assignmentId, studentId);
        return ResponseEntity.ok().build();
    }


    // ================= 2. STUDENT VIEW =================

    // Requirement: View students (under this trainer)
    @GetMapping("/{teacherId}/students")
    public ResponseEntity<List<Student>> getStudentsByTeacher(@PathVariable Long teacherId) {
        return ResponseEntity.ok(teacherService.getStudentsByTeacher(teacherId));
    }


    // ================= 3. SUBMISSIONS & EVALUATION =================

    // Requirement: View student submissions (for this trainer's assignments)
    @GetMapping("/{teacherId}/submissions")
    public ResponseEntity<List<AssignmentSubmission>> getSubmissionsForTeacher(@PathVariable Long teacherId) {
        return ResponseEntity.ok(teacherService.getSubmissionsForTeacher(teacherId));
    }

    // Requirement: Evaluate submissions / Give marks and feedback / Change submission status
    // Trainer ek hi request me marks, feedback aur status (PASSED/FAILED/REVIEWED) bhejega
    @PutMapping("/submissions/{submissionId}/evaluate")
    public ResponseEntity<AssignmentSubmission> evaluateSubmission(
            @PathVariable Long submissionId,
            @RequestParam double marks,
            @RequestParam String feedback,
            @RequestParam String status) { // e.g., "GRADED", "REJECTED"

        return ResponseEntity.ok(
                teacherService.evaluateSubmission(submissionId, marks, feedback, status));
    }

}

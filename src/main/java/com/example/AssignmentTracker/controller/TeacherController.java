package com.example.AssignmentTracker.controller;


import com.example.AssignmentTracker.entity.Assignment;
import com.example.AssignmentTracker.entity.AssignmentSubmission;
import com.example.AssignmentTracker.entity.Student;
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

    @PostMapping("/{teacherId}/assignments")
    public ResponseEntity<Assignment> createAssignment(
            @PathVariable Long teacherId,
             @RequestBody Assignment assignment) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(teacherService.createAssignment(teacherId, assignment));
    }


    @GetMapping("/{teacherId}/assignments")
    public ResponseEntity<List<Assignment>> getAssignmentsByTeacher(@PathVariable Long teacherId) {
        return ResponseEntity.ok(teacherService.getAssignmentsByTeacher(teacherId));
    }


    @PutMapping("/assignments/{assignmentId}")
    public ResponseEntity<Assignment> updateAssignment(
            @PathVariable Long assignmentId,
            @Valid @RequestBody Assignment assignment) {
        return ResponseEntity.ok(teacherService.updateAssignment(assignmentId, assignment));
    }


    @PostMapping("/assignments/{assignmentId}/students/{studentId}")
    public ResponseEntity<Void> assignAssignmentToStudent(@PathVariable Long assignmentId, @PathVariable Long studentId) {
        teacherService.assignAssignmentToStudent(assignmentId, studentId);
        return ResponseEntity.ok().build();
    }




    // Requirement: View students (under this trainer)
    @GetMapping("/{teacherId}/students")
    public ResponseEntity<List<Student>> getStudentsByTeacher(@PathVariable Long teacherId) {
        return ResponseEntity.ok(teacherService.getStudentsByTeacher(teacherId));
    }




    @GetMapping("/{teacherId}/submissions")
    public ResponseEntity<List<AssignmentSubmission>> getSubmissionsForTeacher(@PathVariable Long teacherId) {
        return ResponseEntity.ok(teacherService.getSubmissionsForTeacher(teacherId));
    }


    @PutMapping("/submissions/{submissionId}/evaluate")
    public ResponseEntity<AssignmentSubmission> evaluateSubmission(@PathVariable Long submissionId, @RequestParam double marks, @RequestParam String feedback,
            @RequestParam String status) {
        return ResponseEntity.ok(teacherService.evaluateSubmission(submissionId, marks, feedback, status));
    }

}

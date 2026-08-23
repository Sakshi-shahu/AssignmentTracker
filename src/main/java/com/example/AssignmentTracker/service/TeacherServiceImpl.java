package com.example.AssignmentTracker.service;

import com.example.AssignmentTracker.Exception.AssignmentNotFoundException;
import com.example.AssignmentTracker.Exception.StudentNotFoundException;
import com.example.AssignmentTracker.Exception.TeacherNotFoundException;
import com.example.AssignmentTracker.entity.*;
import com.example.AssignmentTracker.repository.AssignmentRepository;
import com.example.AssignmentTracker.repository.StudentRepository;
import com.example.AssignmentTracker.repository.SubmissionRepository;
import com.example.AssignmentTracker.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService{




    private final TeacherRepository teacherRepository;
    private final AssignmentRepository assignmentRepository;
    private final StudentRepository studentRepository;
    private final SubmissionRepository submissionRepository;

    // ================= 1. ASSIGNMENT MANAGEMENT =================

    @Override
    public Assignment createAssignment(Long teacherId, Assignment assignment) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new TeacherNotFoundException("Trainer not found with id: " + teacherId));

        assignment.setTeacher(teacher);
        return assignmentRepository.save(assignment);
    }

    @Override
    public List<Assignment> getAssignmentsByTeacher(Long teacherId) {
        // Validation check for teacher existence
        if (!teacherRepository.existsById(teacherId)) {
            throw new TeacherNotFoundException("Trainer not found with id: " + teacherId);
        }
        return assignmentRepository.findByTeacherId(teacherId); // 👈 Repository method checked
    }

    @Override
    public Assignment updateAssignment(Long assignmentId, Assignment assignment) {
        Assignment existingAssignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new AssignmentNotFoundException("Assignment not found with id: " + assignmentId));

        existingAssignment.setTitle(assignment.getTitle());
        existingAssignment.setDescription(assignment.getDescription());
        existingAssignment.setDueDate(assignment.getDueDate());

        return assignmentRepository.save(existingAssignment);
    }

    @Override
    public void assignAssignmentToStudent(Long assignmentId, Long studentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new AssignmentNotFoundException("Assignment not found with id: " + assignmentId));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + studentId));

        // Assuming student has a list of active assignments or via a mapping table
        // 👈 FIXED LOGIC: Student list me add karne ke bajay direct link record create kiya
        AssignmentSubmission initialSubmission = new AssignmentSubmission();
        initialSubmission.setAssignment(assignment);
        initialSubmission.setStudent(student);
        initialSubmission.setStatus(SubmissionStatus.PENDING); // Ensure PENDING exists in your SubmissionStatus enum
        initialSubmission.setSubmissionDate(LocalDateTime.now());
        initialSubmission.setSubmissionFile("NOT_SUBMITTED_YET");
        initialSubmission.setMarks(null);
        initialSubmission.setFeedback(null);
       submissionRepository.save(initialSubmission);
    }

    // ================= 2. STUDENT VIEW =================

    @Override
    public List<Student> getStudentsByTeacher(Long teacherId) {
        if (!teacherRepository.existsById(teacherId)) {
            throw new TeacherNotFoundException("Trainer not found with id: " + teacherId);
        }
        return studentRepository.findByTeacherId(teacherId); // 👈 Trainer ke under assigned students list
    }

    // ================= 3. SUBMISSIONS & EVALUATION =================

    @Override
    public List<AssignmentSubmission> getSubmissionsForTeacher(Long teacherId) {
        if (!teacherRepository.existsById(teacherId)) {
            throw new TeacherNotFoundException("Trainer not found with id: " + teacherId);
        }
        // Custom query to fetch submissions of assignments belonging to this teacher
        return submissionRepository.findByAssignment_Teacher_Id(teacherId); // 👈 Custom JPA query method
    }

    @Override
    public AssignmentSubmission evaluateSubmission(Long submissionId, double marks, String feedback, String status) {
        AssignmentSubmission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found with id: " + submissionId));

        // Evaluate metrics mapping
        submission.setMarks(marks);
        submission.setFeedback(feedback);
        submission.setStatus(SubmissionStatus.valueOf(status.toUpperCase())); // e.g., "GRADED", "REJECTED", "PASSED"

        return submissionRepository.save(submission);
    }



}

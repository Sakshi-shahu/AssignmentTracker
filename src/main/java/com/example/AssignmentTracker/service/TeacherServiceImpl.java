//package com.example.AssignmentTracker.service;
//
//import com.example.AssignmentTracker.Exception.AssignmentNotFoundException;
//import com.example.AssignmentTracker.Exception.StudentNotFoundException;
//import com.example.AssignmentTracker.Exception.TeacherNotFoundException;
//import com.example.AssignmentTracker.entity.*;
//import com.example.AssignmentTracker.repository.AssignmentRepository;
//import com.example.AssignmentTracker.repository.StudentRepository;
//import com.example.AssignmentTracker.repository.SubmissionRepository;
//import com.example.AssignmentTracker.repository.TeacherRepository;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class TeacherServiceImpl implements TeacherService{
//
//
//          private final TeacherRepository teacherRepository;
//      private final AssignmentRepository assignmentRepository;
//    private final StudentRepository studentRepository;
//       private final SubmissionRepository submissionRepository;
//
//
//    @Override
//    public Assignment createAssignment(Long teacherId, Assignment assignment) {
//        Teacher teacher = teacherRepository.findById(teacherId)
//                .orElseThrow(() -> new TeacherNotFoundException("Trainer not found with id: " + teacherId));
//
//        assignment.setTeacher(teacher);
//        assignment.setAssignedDate(java.time.LocalDate.now());
//        return assignmentRepository.save(assignment);
//    }
//
//    @Override
//    public List<Assignment> getAssignmentsByTeacher(Long teacherId) {
//        if (!teacherRepository.existsById(teacherId)) {
//            throw new TeacherNotFoundException("Trainer not found with id: " + teacherId);
//        }
//        return assignmentRepository.findByTeacherId(teacherId);
//    }
//
//    @Override
//    public Assignment updateAssignment(Long assignmentId, Assignment assignment) {
//        Assignment existingAssignment = assignmentRepository.findById(assignmentId)
//                .orElseThrow(() -> new AssignmentNotFoundException("Assignment not found with id: " + assignmentId));
//
//        existingAssignment.setTitle(assignment.getTitle());
//        existingAssignment.setDescription(assignment.getDescription());
//        existingAssignment.setDueDate(assignment.getDueDate());
//
//        return assignmentRepository.save(existingAssignment);
//    }
//
//    @Override
//    public void assignAssignmentToStudent(Long assignmentId, Long studentId) {
//        Assignment assignment = assignmentRepository.findById(assignmentId)
//                .orElseThrow(() -> new AssignmentNotFoundException("Assignment not found with id: " + assignmentId));
//
//        Student student = studentRepository.findById(studentId)
//                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + studentId));
//        AssignmentSubmission initialSubmission = new AssignmentSubmission();
//        initialSubmission.setAssignment(assignment);
//        initialSubmission.setStudent(student);
//        initialSubmission.setStatus(SubmissionStatus.PENDING);
//        initialSubmission.setSubmissionDate(LocalDateTime.now());
//        initialSubmission.setSubmissionFile("NOT_SUBMITTED_YET");
//        initialSubmission.setMarks(null);
//        initialSubmission.setFeedback(null);
//       submissionRepository.save(initialSubmission);
//    }
//
//
//
//    @Override
//    public List<Student> getStudentsByTeacher(Long teacherId) {
//        if (!teacherRepository.existsById(teacherId)) {
//            throw new TeacherNotFoundException("Trainer not found with id: " + teacherId);
//        }
//        return studentRepository.findByTeacherId(teacherId); // 👈 Trainer ke under assigned students list
//    }
//
//
//// student submission
//    @Override
//    public List<AssignmentSubmission> getSubmissionsForTeacher(Long teacherId) {
//        if (!teacherRepository.existsById(teacherId)) {throw new TeacherNotFoundException("Trainer not found with id: " + teacherId);
//        }
//        return submissionRepository.findByAssignment_Teacher_Id(teacherId);
//    }
//
//    @Override
//    public AssignmentSubmission evaluateSubmission(Long submissionId, double marks, String feedback, String status) {
//        AssignmentSubmission submission = submissionRepository.findById(submissionId)
//                .orElseThrow(() -> new RuntimeException("Submission not found with id: " + submissionId));
//        submission.setMarks(marks);
//        submission.setFeedback(feedback);
//        submission.setStatus(SubmissionStatus.valueOf(status.toUpperCase()));
//        return submissionRepository.save(submission);
//    }
//
//
//
//}

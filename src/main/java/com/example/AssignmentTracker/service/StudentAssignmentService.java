//package com.example.AssignmentTracker.service;
//
//import com.example.AssignmentTracker.Exception.AssignmentNotFoundException;
//import com.example.AssignmentTracker.entity.Assignment;
//import com.example.AssignmentTracker.entity.AssignmentSubmission;
//import com.example.AssignmentTracker.entity.Student;
//import com.example.AssignmentTracker.entity.SubmissionStatus;
//import com.example.AssignmentTracker.repository.AssignmentRepository;
//import com.example.AssignmentTracker.repository.StudentRepository;
//import com.example.AssignmentTracker.repository.SubmissionRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class StudentAssignmentService {
//
//    private final AssignmentRepository assignmentRepository;
//    private final StudentRepository studentRepository;
//    private final SubmissionRepository submissionRepository;
//
//    public List<Assignment> getAssignmentsByCourse(String course) {
//        return assignmentRepository.findByCourse(course);
//    }
//
//    public List<Assignment> getAssignmentAll() {
//        return assignmentRepository.findAll();
//    }
//
//    public Assignment assignAssignment(Long assignmentId) {
//
//        Assignment assignment = assignmentRepository.findById(assignmentId)
//                .orElseThrow(() -> new AssignmentNotFoundException("Assignment not found"));
//
//        List<Student> students =
//                studentRepository.findByCourse(assignment.getCourse());
//
//        for (Student student : students) {
//
//            assignment.getStudents().add(student);
//            AssignmentSubmission submission = new AssignmentSubmission();
//            submission.setAssignment(assignment);
//            submission.setStudent(student);
//            submission.setStatus(SubmissionStatus.PENDING);
//            submission.setSubmissionDate(LocalDateTime.now());
//            submission.setSubmissionFile("PENDING");
//
//            submissionRepository.save(submission);
//        }
//
//        assignmentRepository.save(assignment);
//        return assignment;
//    }
//
//}
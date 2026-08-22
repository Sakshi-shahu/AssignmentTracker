package com.example.AssignmentTracker.service;

import com.example.AssignmentTracker.Exception.*;
import com.example.AssignmentTracker.entity.Assignment;
import com.example.AssignmentTracker.entity.AssignmentSubmission;
import com.example.AssignmentTracker.entity.Student;
import com.example.AssignmentTracker.entity.SubmissionStatus;
import com.example.AssignmentTracker.repository.AssignmentRepository;
import com.example.AssignmentTracker.repository.StudentRepository;
import com.example.AssignmentTracker.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final AssignmentRepository assignmentRepository;
    private final StudentRepository studentRepository;




    public AssignmentSubmission assignmentSubmission(AssignmentSubmission submission, Long assignmentId, Long studentId, MultipartFile file) {

        Assignment assignment = assignmentRepository.findById(assignmentId)
                        .orElseThrow(() -> new AssignmentNotFoundException("Assignment not found with id: " + assignmentId));

        Student student = studentRepository.findById(studentId)
                        .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + studentId));


        // File check
        if (file == null || file.isEmpty()) {
            throw new FileRequiredException("Please upload a file");
        }


        // Deadline check
        LocalDateTime submissionTime = LocalDateTime.now();

        if (submissionTime.toLocalDate().isAfter(assignment.getDueDate())) {

            throw new SubmissionDeadlineException("Submission deadline has passed");}


        // Save file
        try {

            String uploadDirectory = "uploads/";

            Path directory = Paths.get(uploadDirectory);

            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = directory.resolve(filename);
            Files.write(filePath, file.getBytes());


            // Set submission details

            submission.setSubmissionFile(filePath.toString());
            submission.setAssignment(assignment);
            submission.setStudent(student);
            submission.setSubmissionDate(submissionTime);
            submission.setStatus(SubmissionStatus.SUBMITTED);
            return submissionRepository.save(submission);


        } catch (IOException e) {
            throw new FileStorageException("Failed to store uploaded file", e);
        }
    }




    public AssignmentSubmission updateStudentSubmission(
            Long submissionId,
            MultipartFile file) {


        AssignmentSubmission submission = submissionRepository.findById(submissionId)
                        .orElseThrow(() -> new AssignmentSubmissionNotFoundException("Submission not found with id: " + submissionId));


        // Check file
        if (file == null || file.isEmpty()) {
            throw new FileRequiredException("Please upload a file");
        }

        Assignment assignment = submission.getAssignment();
        LocalDateTime currentTime = LocalDateTime.now();
        if (currentTime.toLocalDate().isAfter(assignment.getDueDate())) {

            throw new SubmissionDeadlineException("Cannot update submission after due date");
        }

        try {
            String uploadDirectory = "uploads/";
            Path directory = Paths.get(uploadDirectory);


            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }


            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();


            Path filePath = directory.resolve(filename);


            Files.write(filePath, file.getBytes()
            );


            // Replace old file path

            submission.setSubmissionFile(filePath.toString());
            submission.setSubmissionDate(currentTime);
            submission.setStatus(SubmissionStatus.SUBMITTED);

            return submissionRepository.save(submission);


        } catch (IOException e) {

            throw new FileStorageException("Failed to update uploaded file", e);
        }
    }




    public AssignmentSubmission getSubmission(Long id) {

        return submissionRepository.findById(id).orElseThrow(() ->
                new AssignmentSubmissionNotFoundException("Submission not found with id: " + id));
    }




    public List<AssignmentSubmission> getMySubmissions(Long studentId) {
        return submissionRepository.findByStudentId(studentId);
    }



    public List<AssignmentSubmission> getAllSubmission() {
        return submissionRepository.findAll();
    }




    public void deleteSubmission(Long id) {

        AssignmentSubmission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new AssignmentSubmissionNotFoundException("Submission not found with id: " + id));
        submissionRepository.delete(submission);
    }


    public void deleteAllSubmission() {
        submissionRepository.deleteAll();
    }


    public AssignmentSubmission update(AssignmentSubmission assignmentSubmission, Long id) {


        AssignmentSubmission submission = submissionRepository.findById(id)
                        .orElseThrow(() -> new AssignmentSubmissionNotFoundException("Submission not found with id: " + id));


        // Trainer updates these

        submission.setStatus(assignmentSubmission.getStatus());
        submission.setMarks(assignmentSubmission.getMarks());
        submission.setFeedback(assignmentSubmission.getFeedback());

        return submissionRepository.save(submission);
    }




    public AssignmentSubmission getStudentAssignmentSubmission(Long assignmentId, Long studentId) {

        return submissionRepository.findByAssignmentIdAndStudentId(assignmentId, studentId)
                .orElseThrow(() -> new AssignmentSubmissionNotFoundException("Submission not found"));
    }
}
package com.example.AssignmentTracker.service;

import com.example.AssignmentTracker.dto.EvaluateSubmissionRequestDto;
import com.example.AssignmentTracker.dto.SubmissionResponseDto;
//import com.example.AssignmentTracker.dto.SubmissionStatusResponse;
import com.example.AssignmentTracker.Exception.*;
import com.example.AssignmentTracker.entity.*;
import com.example.AssignmentTracker.repository.AssignmentRepository;
import com.example.AssignmentTracker.repository.StudentRepository;
import com.example.AssignmentTracker.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl  implements  SubmissionService{

    private final SubmissionRepository submissionRepository;
//    private final AssignmentRepository assignmentRepository;
//    private final StudentRepository studentRepository;
//
//
//    public AssignmentSubmission assignmentSubmission(AssignmentSubmission submission, Long assignmentId, Long studentId, MultipartFile file) {
//
//        Assignment assignment = assignmentRepository.findById(assignmentId)
//                .orElseThrow(() -> new AssignmentNotFoundException("Assignment not found with id: " + assignmentId));
//        Student student = studentRepository.findById(studentId)
//                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + studentId));
//
//        if (file == null || file.isEmpty()) {
//            throw new FileRequiredException("Please upload a file");
//        }
//        LocalDateTime submissionTime = LocalDateTime.now();
//        if (submissionTime.toLocalDate().isAfter(assignment.getDueDate())) {
//            throw new SubmissionDeadlineException("Submission deadline has passed");
//        }
//
//
//        try {
//
//            String uploadDirectory = "uploads/";
//            Path directory = Paths.get(uploadDirectory);
//            if (!Files.exists(directory)) {
//                Files.createDirectories(directory);}
//            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
//            Path filePath = directory.resolve(filename);
//            Files.write(filePath, file.getBytes());
//            submission.setSubmissionFile(filePath.toString());
//            submission.setAssignment(assignment);
//            submission.setStudent(student);
//            submission.setSubmissionDate(submissionTime);
//            submission.setStatus(SubmissionStatus.SUBMITTED);
//            return submissionRepository.save(submission);
//
//        } catch (IOException e) {
//            throw new FileStorageException("Failed to store uploaded file", e);
//        }
//    }
//
//
//    public AssignmentSubmission updateStudentSubmission(Long submissionId,MultipartFile file) {
//        AssignmentSubmission submission = submissionRepository.findById(submissionId)
//                .orElseThrow(() -> new AssignmentSubmissionNotFoundException("Submission not found with id: " + submissionId));
//        if (file == null || file.isEmpty()) {
//            throw new FileRequiredException("Please upload a file");
//        }
//        Assignment assignment = submission.getAssignment();
//        LocalDateTime currentTime = LocalDateTime.now();
//        if (currentTime.toLocalDate().isAfter(assignment.getDueDate())) {
//            throw new SubmissionDeadlineException("Cannot update submission after due date");
//        }
//
//        try {
//            String uploadDirectory = "uploads/";
//            Path directory = Paths.get(uploadDirectory);
//            if (!Files.exists(directory)) {
//                Files.createDirectories(directory);}
//            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
//            Path filePath = directory.resolve(filename);
//            Files.write(filePath, file.getBytes());
//            submission.setSubmissionFile(filePath.toString());
//            submission.setSubmissionDate(currentTime);
//            submission.setStatus(SubmissionStatus.SUBMITTED);
//            return submissionRepository.save(submission);
//
//        } catch (IOException e) {
//            throw new FileStorageException("Failed to update uploaded file", e);
//        }
//    }
//
//
//    public AssignmentSubmission getSubmission(Long id) {
//        return submissionRepository.findById(id).orElseThrow(() ->
//                new AssignmentSubmissionNotFoundException("Submission not found with id: " + id));
//    }
//
//
//    public List<AssignmentSubmission> getMySubmissions(Long studentId) {
//        return submissionRepository.findByStudentId(studentId);
//    }
//    public List<AssignmentSubmission> getAllSubmissions() {
//        return submissionRepository.findAll();
//    }
//
//
//
//    public void deleteSubmission(Long id) {
//
//        AssignmentSubmission submission = submissionRepository.findById(id)
//                .orElseThrow(() -> new AssignmentSubmissionNotFoundException("Submission not found with id: " + id));
//        submissionRepository.delete(submission);
//    }
//
//
//    public void deleteAllSubmission() {
//        submissionRepository.deleteAll();
//    }
//
//    public AssignmentSubmission update(AssignmentSubmission assignmentSubmission, Long id) {
//        AssignmentSubmission submission = submissionRepository.findById(id)
//                .orElseThrow(() -> new AssignmentSubmissionNotFoundException("Submission not found with id: " + id));
//        submission.setStatus(assignmentSubmission.getStatus());
//        submission.setMarks(assignmentSubmission.getMarks());
//        submission.setFeedback(assignmentSubmission.getFeedback());
//        return submissionRepository.save(submission);
//    }
//
//    public Optional<AssignmentSubmission> getSubmission(Long studentId, Long assignmentId) {
//        return submissionRepository.findByAssignment_IdAndStudent_Id(assignmentId, studentId);
//    }



    // by kashish



    private final ModelMapper modelMapper;

    @Value("${file.upload-dir}")
    private String uploadDirectory;

    @Override
    public List<SubmissionResponseDto>
    getTeacherSubmissions(Long teacherId) {

        return submissionRepository
                .findByAssignmentTeacherId(teacherId)
                .stream()
                .map(entity ->
                        modelMapper.map(
                                entity,
                                SubmissionResponseDto.class
                        )
                )
                .toList();
    }

    @Override
    public SubmissionResponseDto getSubmission(
            Long submissionId,
            Long teacherId
    ) {

        AssignmentSubmission submission =
                submissionRepository.findById(submissionId)
                        .orElseThrow(() ->
                                new SubmissionNotFoundException(
                                        "Submission not found with id: "
                                                + submissionId
                                )
                        );

        validateTeacher(
                submission,
                teacherId
        );

        return modelMapper.map(
                submission,
                SubmissionResponseDto.class
        );
    }

    @Override
    public SubmissionResponseDto evaluateSubmission(
            Long submissionId,
            EvaluateSubmissionRequestDto request,
            Long teacherId
    ) {

        AssignmentSubmission submission =
                submissionRepository.findById(submissionId)
                        .orElseThrow(() ->
                                new SubmissionNotFoundException(
                                        "Submission not found with id: "
                                                + submissionId
                                )
                        );

        validateTeacher(
                submission,
                teacherId
        );

        // IMPORTANT CHECK
        validateSubmissionFile(submission);

        // Marks cannot exceed max marks
        if (request.getMarks()
                > submission.getAssignment().getMaxMarks()) {

            throw new InvalidMarksException(
                    "Marks cannot be greater than maximum marks"
            );
        }

        submission.setMarks(
                request.getMarks()
        );

        submission.setFeedback(
                request.getFeedback()
        );

        submission.setStatus(
                request.getStatus()
        );

        submission.setEvaluatedAt(
                LocalDateTime.now()
        );

        AssignmentSubmission updated =
                submissionRepository.save(submission);

        return modelMapper.map(
                updated,
                SubmissionResponseDto.class
        );
    }

    private void validateTeacher(
            AssignmentSubmission submission,
            Long teacherId
    ) {

        if (!submission.getAssignment()
                .getTeacher()
                .getId()
                .equals(teacherId)) {

            throw new UnauthorizedException(
                    "You cannot access this submission"
            );
        }
    }

    private void validateSubmissionFile(
            AssignmentSubmission submission
    ) {

        String file = submission.getSubmissionFile();

        if (file == null || file.isBlank()) {

            throw new SubmissionFileNotFoundException(
                    "Student has not submitted any file"
            );
        }

        Path path =
                Paths.get(uploadDirectory)
                        .resolve(file)
                        .normalize();

        if (!Files.exists(path)
                || !Files.isRegularFile(path)) {

            throw new SubmissionFileNotFoundException(
                    "Submission file does not exist"
            );
        }
    }





}

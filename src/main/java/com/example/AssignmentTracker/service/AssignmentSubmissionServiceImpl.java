package com.example.AssignmentTracker.service;

import com.example.AssignmentTracker.Dto.AssignmentSubmissionResponse;
import com.example.AssignmentTracker.Exception.FileRequiredException;
import com.example.AssignmentTracker.Exception.SubmissionDeadlineException;
import com.example.AssignmentTracker.entity.Assignment;
import com.example.AssignmentTracker.entity.AssignmentSubmission;
import com.example.AssignmentTracker.entity.Student;
import com.example.AssignmentTracker.entity.SubmissionStatus;
import com.example.AssignmentTracker.Exception.ResourceNotFoundException;
import com.example.AssignmentTracker.repository.AssignmentRepository;
import com.example.AssignmentTracker.repository.AssignmentSubmissionRepository;
import com.example.AssignmentTracker.repository.StudentRepository;
import com.example.AssignmentTracker.service.AssignmentSubmissionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssignmentSubmissionServiceImpl implements AssignmentSubmissionService {

    private final AssignmentSubmissionRepository submissionRepository;
    private final AssignmentRepository assignmentRepository;
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;
    private final IdempotencyService idempotencyService;
    @Override
    public AssignmentSubmissionResponse submitAssignmentFile(Long studentId, Long assignmentId, MultipartFile file, String idempotencyKey) {
        if (idempotencyService.isProceed(idempotencyKey)) {
            Long submissionId = idempotencyService.get(idempotencyKey);
            AssignmentSubmission existing = submissionRepository.findById(submissionId)
                    .orElseThrow(() -> new ResourceNotFoundException("Submission not found"));

            AssignmentSubmissionResponse response = modelMapper.map(existing, AssignmentSubmissionResponse.class);
            response.setStatus("ALREADY_PROCESSED"); // or add a custom field like response.setMessage("Request already processed");
            return response;



        }



        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found"));

        if (file == null || file.isEmpty()) {
            throw new FileRequiredException("Please upload a file");
        }

        LocalDateTime currentTime = LocalDateTime.now();
        if (assignment.getDueDate() != null && currentTime.toLocalDate().isAfter(assignment.getDueDate())) {
            throw new SubmissionDeadlineException("Cannot submit after due date");
        }

        try {
            String uploadDirectory = "uploads/";
            Path directory = Paths.get(uploadDirectory);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }

            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = directory.resolve(filename);
            Files.write(filePath, file.getBytes());

            AssignmentSubmission submission = new AssignmentSubmission();
            submission.setStudent(student);
            submission.setAssignment(assignment);
            submission.setSubmissionDate(currentTime);
            submission.setSubmissionFile(filePath.toString());
            submission.setStatus(SubmissionStatus.SUBMITTED);

            submissionRepository.save(submission);
            idempotencyService.put(idempotencyKey, submission.getId());
            return modelMapper.map(submission, AssignmentSubmissionResponse.class);

        } catch (IOException e) {
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
    }
    @Override
    public AssignmentSubmissionResponse updateSubmissionFile(Long studentId, Long assignmentId, MultipartFile file, String idempotencyKey) {

        if (idempotencyService.isProceed(idempotencyKey)) {
            Long submissionId = idempotencyService.get(idempotencyKey);
            AssignmentSubmission existing = submissionRepository.findById(submissionId)
                    .orElseThrow(() -> new ResourceNotFoundException("Submission not found"));

            AssignmentSubmissionResponse response = modelMapper.map(existing, AssignmentSubmissionResponse.class);
            response.setStatus("ALREADY_PROCESSED"); // or add a custom field like response.setMessage("Request already processed");
            return response;        }

        AssignmentSubmission submission = submissionRepository.findByStudentIdAndAssignmentId(studentId, assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Submission not found"));

        if (submission.getStatus() == SubmissionStatus.EVALUATED) {
            throw new RuntimeException("Submission cannot be updated after evaluation");
        }

        if (file == null || file.isEmpty()) {
            throw new FileRequiredException("Please upload a file");
        }

        Assignment assignment = submission.getAssignment();
        LocalDateTime currentTime = LocalDateTime.now();
        if (assignment.getDueDate() != null && currentTime.toLocalDate().isAfter(assignment.getDueDate())) {
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
            Files.write(filePath, file.getBytes());

            submission.setSubmissionFile(filePath.toString());
            submission.setSubmissionDate(currentTime);
            submission.setStatus(SubmissionStatus.SUBMITTED);

            submissionRepository.save(submission);

            idempotencyService.put(idempotencyKey, submission.getId());

            return modelMapper.map(submission, AssignmentSubmissionResponse.class);

        } catch (IOException e) {
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
    }


    @Override
    public List<AssignmentSubmissionResponse> getStudentSubmissions(Long studentId) {
        List<AssignmentSubmission> submissions = submissionRepository.findByStudentId(studentId);
        List<AssignmentSubmissionResponse> responses = new ArrayList<>();

        for (AssignmentSubmission submission : submissions) {
            AssignmentSubmissionResponse response = modelMapper.map(submission, AssignmentSubmissionResponse.class);
            responses.add(response);
        }
        return responses;
    }

    @Override
    public AssignmentSubmissionResponse getSubmission(Long studentId, Long assignmentId) {
        AssignmentSubmission submission = submissionRepository.findByStudentIdAndAssignmentId(studentId, assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Submission not found"));
        return modelMapper.map(submission, AssignmentSubmissionResponse.class);
    }
}

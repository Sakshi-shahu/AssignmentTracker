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

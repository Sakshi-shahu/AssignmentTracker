package com.example.AssignmentTracker.service;


import com.example.AssignmentTracker.Exception.*;
import com.example.AssignmentTracker.entity.Assignment;
import com.example.AssignmentTracker.entity.AssignmentSubmission;
import com.example.AssignmentTracker.entity.Student;
import com.example.AssignmentTracker.entity.SubmissionStatus;
import com.example.AssignmentTracker.repository.AssignmentRepository;
import com.example.AssignmentTracker.repository.StudentRepository;
import com.example.AssignmentTracker.repository.SubmissionRepository;
import jakarta.validation.Valid;
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


    public AssignmentSubmission assignmentSubmission(AssignmentSubmission submission, Long assignment_id, Long student_id, MultipartFile file)  {

        Assignment assignment= assignmentRepository.findById(assignment_id).orElseThrow(()->
                new AssignmentNotFoundException(
                        "Assignment not found with id: " + assignment_id
                ));

        Student student= studentRepository.findById(student_id).orElseThrow(()->
                new StudentNotFoundException("student not found by this id"+student_id));


        if (file == null || file.isEmpty()) {
            throw new FileRequiredException("Please upload a file");
        }

//due date Maine check ki
        LocalDateTime submissionTime = LocalDateTime.now();
        if (submissionTime.toLocalDate().isAfter(assignment.getDueDate())) {

            throw new SubmissionDeadlineException("Submission deadline has passed");
        }

        //file save karungi
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


            submission.setAssignment(assignment);
            submission.setStudent(student);
            submission.setSubmissionDate(submissionTime);
            submission.setStatus(SubmissionStatus.SUBMITTED);


            return submissionRepository.save(submission);
        }
       catch (IOException e) {
            throw new FileStorageException("Failed to store uploaded file", e);
        }
    }

    public AssignmentSubmission getSubmission(Long id){
      return   submissionRepository.findById(id).orElseThrow(()-> new AssignmentSubmissionNotFoundException("submission not found by this id"));
    }

    public List<AssignmentSubmission> getAllSubmission(){
        return submissionRepository.findAll();
    }

    public void deleteSubmission(Long id){
        AssignmentSubmission submission= submissionRepository.findById(id).orElseThrow(()-> new AssignmentSubmissionNotFoundException("submission not found by this id"));
        submissionRepository.delete(submission);
    }

    public void deleteAllSubmission(){
        submissionRepository.deleteAll();
    }

    public AssignmentSubmission update(AssignmentSubmission assignmentSubmission,Long id){
        AssignmentSubmission submission= submissionRepository.findById(id).orElseThrow(()-> new AssignmentSubmissionNotFoundException("submission not found by this id"));

        submission.setStatus(assignmentSubmission.getStatus());
        submission.setMarks(assignmentSubmission.getMarks());
        submission.setFeedback(assignmentSubmission.getFeedback());

        return submissionRepository.save(submission);
    }


}



package com.example.AssignmentTracker.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
public class SubmissionStatusResponse {
    @JsonIgnore
    @Id
    private Long studentId;
    private String studentName;

    private String assignmentTitle;

    private String teacherName;
    private String subjectTeacher;

    private String status;


}

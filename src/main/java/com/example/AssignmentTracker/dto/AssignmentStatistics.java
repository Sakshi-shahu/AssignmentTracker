package com.example.AssignmentTracker.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentStatistics {

    private long totalAssignments;

    private long totalSubmissions;

    private long totalAdmins;

    private long totalTrainers;

    private long totalStudents;
}
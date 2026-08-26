package com.example.AssignmentTracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;       // CREATE, UPDATE, DELETE, SUBMIT
    private String entityName;   // Student / AssignmentSubmission
    private Long entityId;       // ID of entity
    private String status;       // SUCCESS / FAILED
    private String message;      // Detailed message
    private LocalDateTime dateTime;
}

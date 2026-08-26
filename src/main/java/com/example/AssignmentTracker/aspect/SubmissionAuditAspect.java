package com.example.AssignmentTracker.aspect;

import com.example.AssignmentTracker.entity.AuditLog;
import com.example.AssignmentTracker.entity.AssignmentSubmission;
import com.example.AssignmentTracker.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class SubmissionAuditAspect {

    private final AuditLogRepository auditLogRepository;

    @AfterReturning(pointcut = "execution(* com.example.AssignmentTracker.service.AssignmentSubmissionService.submitAssignment(..))", returning = "result")
    public void afterSubmit(Object result) {
        AssignmentSubmission submission = (AssignmentSubmission) result;
        log.info("Submission created successfully. ID: {}", submission.getId());
        saveAudit("SUBMIT", "AssignmentSubmission", submission.getId(), "SUCCESS", "Submission created successfully");
    }

    @AfterThrowing(pointcut = "execution(* com.example.AssignmentTracker.service.AssignmentSubmissionService.*(..))", throwing = "exception")
    public void afterSubmissionException(JoinPoint joinPoint, Exception exception) {
        log.error("Submission action failed. Method: {} | Error: {}", joinPoint.getSignature(), exception.getMessage());
        saveAudit("SUBMISSION_ACTION", "AssignmentSubmission", null, "FAILED", exception.getMessage());
    }

    private void saveAudit(String action, String entityName, Long entityId, String status, String message) {
        AuditLog auditLog = AuditLog.builder()
                .action(action)
                .entityName(entityName)
                .entityId(entityId)
                .status(status)
                .message(message)
                .dateTime(LocalDateTime.now())
                .build();
        auditLogRepository.save(auditLog);
    }
}

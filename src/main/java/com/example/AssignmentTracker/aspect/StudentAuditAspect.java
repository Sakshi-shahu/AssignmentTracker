package com.example.AssignmentTracker.aspect;

import com.example.AssignmentTracker.Dto.StudentResponse;
import com.example.AssignmentTracker.entity.AuditLog;
import com.example.AssignmentTracker.entity.Student;
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
public class StudentAuditAspect {

    private final AuditLogRepository auditLogRepository;

    @AfterReturning(pointcut = "execution(* com.example.AssignmentTracker.service.StudentService.addStudent(..))", returning = "result")
    public void afterAddStudent(Object result) {
        StudentResponse student = (StudentResponse) result;
        log.info("Student created successfully. ID: {}", student.getId());
        saveAudit("CREATE", "Student", student.getId(), "SUCCESS", "Student created successfully");
    }

    @AfterReturning(pointcut = "execution(* com.example.AssignmentTracker.service.StudentService.updateStudent(..))", returning = "result")
    public void afterUpdateStudent(Object result) {
        Student student = (Student) result;
        log.info("Student updated successfully. ID: {}", student.getId());
        saveAudit("UPDATE", "Student", student.getId(), "SUCCESS", "Student updated successfully");
    }

    @AfterReturning(pointcut = "execution(* com.example.AssignmentTracker.service.StudentService.deleteStudent(..))")
    public void afterDeleteStudent(JoinPoint joinPoint) {
        Long studentId = (Long) joinPoint.getArgs()[0];
        log.info("Student deleted successfully. ID: {}", studentId);
        saveAudit("DELETE", "Student", studentId, "SUCCESS", "Student deleted successfully");
    }

    @AfterThrowing(pointcut = "execution(* com.example.AssignmentTracker.service.StudentService.*(..))", throwing = "exception")
    public void afterThrowingException(JoinPoint joinPoint, Exception exception) {
        log.error("Student action failed. Method: {} | Error: {}", joinPoint.getSignature(), exception.getMessage());
        saveAudit("ACTION", "Student", null, "FAILED", exception.getMessage());
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

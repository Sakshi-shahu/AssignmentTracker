package com.example.AssignmentTracker.aspect;

import com.example.AssignmentTracker.entity.AuditLog;
import com.example.AssignmentTracker.entity.Student;
import com.example.AssignmentTracker.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class StudentAuditAspect {
    private final AuditLogRepository auditLogRepository;

    @AfterReturning(
            pointcut = "execution(* com.example.AssignmentTracker.service.StudentService.addStudent(..))",
            returning = "result")

    public void afterAddStudent(Object result){
        Student student=(Student) result;
        log.info("Student created successfully. ID: {}",student.getId());

        AuditLog auditLog=new AuditLog();
        auditLog.setAction("CREATE");
        auditLog.setEntityName("Student");
        auditLog.setEntityId(student.getId());
        auditLog.setStatus("SUCCESS");
        auditLog.setMessage("Student created successfully");
        auditLog.setDateTime(LocalDateTime.now());
        auditLogRepository.save(auditLog);
    }

    @AfterReturning(
            pointcut = "execution(* com.example.AssignmentTracker.service.StudentService.updateStudent(..))",
            returning = "result")
    public void afterUpdateStudent(Object result){
        Student student=(Student) result;
        log.info("Student updated successfully. ID: {}",student.getId());
        AuditLog auditLog=new AuditLog();
        auditLog.setAction("UPDATE");
        auditLog.setEntityName("Student");
        auditLog.setEntityId(student.getId());
        auditLog.setStatus("SUCCESS");
        auditLog.setMessage("Student Updated successfully");
        auditLog.setDateTime(LocalDateTime.now());

        auditLogRepository.save(auditLog);

    }


    @AfterReturning(
            pointcut = "execution(* com.example.AssignmentTracker.service.StudentService.deleteStudent(..))")
    public void afterDeleteStudent(JoinPoint joinPoint){
        Long studentId= (Long) joinPoint.getArgs()[0];
        log.info("Student deleted successfully. ID: {}",studentId);
        AuditLog auditLog=new AuditLog();
        auditLog.setAction("Delete");
        auditLog.setEntityName("Student");
        auditLog.setEntityId(studentId);
        auditLog.setStatus("SUCCESS");
        auditLog.setMessage("Student deleted successfully");
        auditLog.setDateTime(LocalDateTime.now());
        auditLogRepository.save(auditLog);

    }



    @AfterThrowing(
            pointcut = "execution(* com.example.AssignmentTracker.service.StudentService.addStudent(..))",
            throwing = "exception"
    )
    public void afterThrowingException(JoinPoint joinPoint,Exception exception){
        log.error(  "Student creation failed. Error: {}", exception.getMessage());

        AuditLog auditLog=new AuditLog();
        auditLog.setAction("CREATE");
        auditLog.setEntityName("Student");
        auditLog.setEntityId(null);
        auditLog.setStatus("FAILED");
        auditLog.setMessage(exception.getMessage());
        auditLog.setDateTime(LocalDateTime.now());

        auditLogRepository.save(auditLog);
    }





@AfterThrowing(
        pointcut = "execution(* com.example.AssignmentTracker.service.StudentService.updateStudent(..))",
        throwing = "exception"
)
public void afterThrowingUpdateException(JoinPoint joinPoint,Exception exception){
    log.error(  "Student update failed. Error: {}", exception.getMessage());
    AuditLog auditLog=new AuditLog();
    auditLog.setAction("UPDATE");
    auditLog.setEntityName("Student");
    auditLog.setEntityId(null);
    auditLog.setStatus("FAILED");
    auditLog.setMessage(exception.getMessage());
    auditLog.setDateTime(LocalDateTime.now());

    auditLogRepository.save(auditLog);

}



@AfterThrowing(
        pointcut = "execution(* com.example.AssignmentTracker.service.StudentService.deleteStudent(..))",
        throwing = "exception"
)

public void afterThrowingDeleteException(JoinPoint joinPoint,Exception exception){
    log.error(  "Student delete failed. Error: {}", exception.getMessage());

    AuditLog auditLog=new AuditLog();
    auditLog.setAction("DELETE");
    auditLog.setEntityName("Student");
    auditLog.setEntityId(null);
    auditLog.setStatus("FAILED");
    auditLog.setMessage(exception.getMessage());
    auditLog.setDateTime(LocalDateTime.now());

    auditLogRepository.save(auditLog);
}


    }




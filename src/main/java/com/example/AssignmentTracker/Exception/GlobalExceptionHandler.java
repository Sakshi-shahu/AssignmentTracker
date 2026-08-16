package com.example.AssignmentTracker.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentNotFoundException.class)
        public ResponseEntity<?> handleNotFoundException(StudentNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(404, exception.getMessage(),
                LocalDateTime.now()));
    }

    public record ErrorResponse(
            int status,
            String message,
            LocalDateTime dateTime
    ){


    }

    @ExceptionHandler(AssignmentSubmissionNotFoundException.class)
    public ResponseEntity<?> handleSubmissionNotFoundException(AssignmentSubmissionNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(404, exception.getMessage(),
                LocalDateTime.now()));
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<?> handleFileStorageException(FileStorageException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, ex.getMessage(), LocalDateTime.now()));
    }



    }


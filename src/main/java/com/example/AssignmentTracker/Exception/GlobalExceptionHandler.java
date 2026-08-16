package com.example.AssignmentTracker.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(studentNotFoundException.class)
        public ResponseEntity<?> handleNotFoundException(studentNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(404, exception.getMessage(),
                LocalDateTime.now()));
    }

    public record ErrorResponse(
            int status,
            String message,
            LocalDateTime dateTime
    ){


    }
}

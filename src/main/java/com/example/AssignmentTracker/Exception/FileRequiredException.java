package com.example.AssignmentTracker.Exception;

public class FileRequiredException extends RuntimeException {
    public FileRequiredException(String message) {
        super(message);
    }
}

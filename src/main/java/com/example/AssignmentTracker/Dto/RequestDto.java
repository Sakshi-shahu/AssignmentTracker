package com.example.AssignmentTracker.Dto;


import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestDto {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter valid email")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Phone is required")
    @Size(min = 10, max = 10, message = "Phone must be 10 digits")
    private String phone;

    @NotBlank(message = "Course is required")
    private String course;

}

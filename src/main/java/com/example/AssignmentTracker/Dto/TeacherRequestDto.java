package com.example.AssignmentTracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TeacherRequestDto {
//morning ka changes
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Enter a valid email")
    private String email;

    @NotBlank(message = "Phone cannot be blank")
    @Size(min = 10, max = 10, message = "Phone must contain 10 digits")
    private String phone;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    @NotBlank(message = "Subject cannot be blank")
    private String subject;

    private boolean active = true;

}

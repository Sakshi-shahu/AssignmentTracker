package com.example.AssignmentTracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponseDto {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private Boolean active;
}
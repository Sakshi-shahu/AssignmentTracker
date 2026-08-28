package com.example.AssignmentTracker.dto;

import lombok.Data;

@Data
public class SuperAdminResponseDto {

    private Long id;

    private String name;

    private String email;

    private String phone;

    private boolean active;
}
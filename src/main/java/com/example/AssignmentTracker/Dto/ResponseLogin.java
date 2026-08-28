package com.example.AssignmentTracker.Dto;

import lombok.Data;

@Data
public class ResponseLogin {

    private Long id;
    private String username;
    private String email;
    private String role;
    private String token;
}

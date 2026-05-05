package com.example.demo_project_auth_and_scaling.auth.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String institution;
}

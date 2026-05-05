package com.example.demo_project_auth_and_scaling.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor // kontruktort general
public class AuthResponse {
    private String token;
}

package com.example.demo_project_auth_and_scaling.analytics;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuspiciousUserResponse {
    private String email;
    private String reason;
}
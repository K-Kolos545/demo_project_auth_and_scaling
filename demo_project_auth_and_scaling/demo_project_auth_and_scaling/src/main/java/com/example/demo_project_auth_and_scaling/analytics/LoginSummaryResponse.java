package com.example.demo_project_auth_and_scaling.analytics;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginSummaryResponse {
    private long successfulLogins;
    private long failedLogins;
}

package com.example.demo_project_auth_and_scaling.mock.dto;

import lombok.Data;

@Data
public class ExternalAuthResponse {
    private String email;
    private String institution;
    private String eduPersonPrincipalName;
    private String provider;
}

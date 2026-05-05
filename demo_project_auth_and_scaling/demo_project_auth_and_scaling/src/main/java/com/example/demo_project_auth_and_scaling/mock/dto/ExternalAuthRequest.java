package com.example.demo_project_auth_and_scaling.mock.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalAuthRequest {
    private String email;
    private String password;
}

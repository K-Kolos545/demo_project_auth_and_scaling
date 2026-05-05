package com.example.outside_provider.dto;


import lombok.Data;

@Data
public class ExternalAuthRequest {
    private String email;
    private String password;
}

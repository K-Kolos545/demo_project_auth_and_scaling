package com.example.outside_provider.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExternalAuthResponse {
    private String email;
    private String institution;
    private String eduPersonPrincipalName;
    private String provider;
}

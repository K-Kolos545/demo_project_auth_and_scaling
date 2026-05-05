package com.example.outside_provider.controller;

import com.example.outside_provider.dto.ExternalAuthRequest;
import com.example.outside_provider.dto.ExternalAuthResponse;
import com.example.outside_provider.service.ExternalAuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduid")
public class EduIdController {

    private final ExternalAuthService service;

    public EduIdController(ExternalAuthService service) {
        this.service = service;
    }

    @PostMapping("/authenticate")
    public ExternalAuthResponse authenticate(@RequestBody ExternalAuthRequest request) {
        return service.authenticate(request);
    }
}

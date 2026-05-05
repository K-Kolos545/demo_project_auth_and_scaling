package com.example.demo_project_auth_and_scaling.auth;

import com.example.demo_project_auth_and_scaling.auth.dto.AuthResponse;
import com.example.demo_project_auth_and_scaling.auth.dto.LoginRequest;
import com.example.demo_project_auth_and_scaling.auth.dto.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return service.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody LoginRequest request,
            HttpServletRequest httpRequest
    ) {
        return service.login(request, httpRequest);
    }
}

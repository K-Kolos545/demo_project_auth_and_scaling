package com.example.demo_project_auth_and_scaling.mock;

import com.example.demo_project_auth_and_scaling.auth.dto.AuthResponse;
import com.example.demo_project_auth_and_scaling.user.AppUser;
import com.example.demo_project_auth_and_scaling.user.AuthProvider;
import com.example.demo_project_auth_and_scaling.user.Role;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/mock")
public class MockIdentityController {

    private final MockIdentityService mockIdentityService;

    public MockIdentityController(MockIdentityService mockIdentityService) {
        this.mockIdentityService = mockIdentityService;
    }


    @PostMapping("/oidc/login")
    public AuthResponse oidcLogin(
            @RequestBody MockIdentityRequest request,
            HttpServletRequest httpRequest
    ) {
        return mockIdentityService.mockFederatedLogin(request, AuthProvider.OIDC, httpRequest);
    }

    @PostMapping("/saml/login")
    public AuthResponse samlLogin(
            @RequestBody MockIdentityRequest request,
            HttpServletRequest httpRequest
    ) {
        return mockIdentityService.mockFederatedLogin(request, AuthProvider.SAML, httpRequest);
    }


}

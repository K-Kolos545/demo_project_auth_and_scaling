package com.example.demo_project_auth_and_scaling.mock;

import com.example.demo_project_auth_and_scaling.auth.dto.AuthResponse;
import com.example.demo_project_auth_and_scaling.log.LoginEventService;
import com.example.demo_project_auth_and_scaling.security.JwtService;
import com.example.demo_project_auth_and_scaling.user.AppUser;
import com.example.demo_project_auth_and_scaling.user.AuthProvider;
import com.example.demo_project_auth_and_scaling.user.Role;
import com.example.demo_project_auth_and_scaling.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mock")
public class MockIdentityController {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final LoginEventService loginEventService;

    public MockIdentityController(
            UserRepository userRepository,
            JwtService jwtService,
            LoginEventService loginEventService
    ) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.loginEventService = loginEventService;
    }

    @PostMapping("/oidc/login")
    public AuthResponse oidcLogin(
            @RequestBody MockIdentityRequest request,
            HttpServletRequest httpRequest
    ) {
        return mockFederatedLogin(request, AuthProvider.OIDC, httpRequest);
    }

    @PostMapping("/saml/login")
    public AuthResponse samlLogin(
            @RequestBody MockIdentityRequest request,
            HttpServletRequest httpRequest
    ) {
        return mockFederatedLogin(request, AuthProvider.SAML, httpRequest);
    }

    private AuthResponse mockFederatedLogin(
            MockIdentityRequest request,
            AuthProvider provider,
            HttpServletRequest httpRequest
    ) {
        AppUser user = userRepository.findByEmail(request.getEmail())
                .orElseGet(() -> userRepository.save(
                        AppUser.builder()
                                .email(request.getEmail())
                                .password(null)
                                .role(Role.STUDENT)
                                .institution(request.getInstitution())
                                .eduPersonPrincipalName(request.getEmail())
                                .authProvider(provider)
                                .build()
                ));

        loginEventService.saveEvent(
                user,
                request.getEmail(),
                true,
                provider,
                httpRequest.getRemoteAddr(),
                null
        );

        return new AuthResponse(jwtService.generateToken(user));
    }
}

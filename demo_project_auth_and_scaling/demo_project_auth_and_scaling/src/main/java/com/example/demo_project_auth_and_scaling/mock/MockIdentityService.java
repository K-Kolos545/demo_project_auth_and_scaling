package com.example.demo_project_auth_and_scaling.mock;

import com.example.demo_project_auth_and_scaling.auth.dto.AuthResponse;
import com.example.demo_project_auth_and_scaling.log.LoginEventService;
import com.example.demo_project_auth_and_scaling.mock.dto.ExternalAuthRequest;
import com.example.demo_project_auth_and_scaling.mock.dto.ExternalAuthResponse;
import com.example.demo_project_auth_and_scaling.security.JwtService;
import com.example.demo_project_auth_and_scaling.user.AppUser;
import com.example.demo_project_auth_and_scaling.user.AuthProvider;
import com.example.demo_project_auth_and_scaling.user.Role;
import com.example.demo_project_auth_and_scaling.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MockIdentityService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final LoginEventService loginEventService;
    private final RestTemplate restTemplate;

    @Value("${mock.eduid.url}")
    private String mockEduIdUrl;

    public MockIdentityService(
            UserRepository userRepository,
            JwtService jwtService,
            LoginEventService loginEventService,
            RestTemplate restTemplate
    ) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.loginEventService = loginEventService;
        this.restTemplate = restTemplate;
    }

    public AuthResponse mockFederatedLogin(
            MockIdentityRequest request,
            AuthProvider provider,
            HttpServletRequest httpRequest
    ) {
        ExternalAuthRequest externalRequest =
                new ExternalAuthRequest(request.getEmail(), request.getPassword());

        ExternalAuthResponse externalResponse = restTemplate.postForObject(
                mockEduIdUrl + "/eduid/authenticate",
                externalRequest,
                ExternalAuthResponse.class
        );

        if (externalResponse == null || externalResponse.getEmail() == null) {
            loginEventService.saveEvent(
                    null,
                    request.getEmail(),
                    false,
                    provider,
                    httpRequest.getRemoteAddr(),
                    "External authentication failed"
            );
            throw new RuntimeException("External authentication failed");
        }

        AppUser user = userRepository.findByEmail(externalResponse.getEmail())
                .orElseGet(() -> userRepository.save(
                        AppUser.builder()
                                .email(externalResponse.getEmail())
                                .password(null)
                                .role(Role.STUDENT)
                                .institution(externalResponse.getInstitution())
                                .eduPersonPrincipalName(externalResponse.getEduPersonPrincipalName())
                                .authProvider(provider)
                                .build()
                ));

        loginEventService.saveEvent(
                user,
                user.getEmail(),
                true,
                provider,
                httpRequest.getRemoteAddr(),
                null
        );

        return new AuthResponse(jwtService.generateToken(user));
    }
}
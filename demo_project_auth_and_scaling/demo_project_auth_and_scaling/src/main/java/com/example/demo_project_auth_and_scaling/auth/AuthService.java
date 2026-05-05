package com.example.demo_project_auth_and_scaling.auth;


import com.example.demo_project_auth_and_scaling.auth.dto.AuthResponse;
import com.example.demo_project_auth_and_scaling.auth.dto.LoginRequest;
import com.example.demo_project_auth_and_scaling.auth.dto.RegisterRequest;
import com.example.demo_project_auth_and_scaling.log.LoginEventService;
import com.example.demo_project_auth_and_scaling.user.AppUser;
import com.example.demo_project_auth_and_scaling.user.AuthProvider;
import com.example.demo_project_auth_and_scaling.user.Role;
import com.example.demo_project_auth_and_scaling.user.UserRepository;

import com.example.demo_project_auth_and_scaling.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final LoginEventService loginEventService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            LoginEventService loginEventService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.loginEventService = loginEventService;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User already exists");
        }

        AppUser user = AppUser.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.STUDENT)
                .institution(request.getInstitution())
                .eduPersonPrincipalName(request.getEmail())
                .authProvider(AuthProvider.LOCAL)
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request, HttpServletRequest httpRequest) {
        String ip = httpRequest.getRemoteAddr();

        AppUser user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) {
            loginEventService.saveEvent(
                    null,
                    request.getEmail(),
                    false,
                    AuthProvider.LOCAL,
                    ip,
                    "User not found"
            );
            throw new RuntimeException("Invalid credentials");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            loginEventService.saveEvent(
                    user,
                    request.getEmail(),
                    false,
                    AuthProvider.LOCAL,
                    ip,
                    "Invalid password"
            );
            throw new RuntimeException("Invalid credentials");
        }

        loginEventService.saveEvent(
                user,
                user.getEmail(),
                true,
                AuthProvider.LOCAL,
                ip,
                null
        );

        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }
}

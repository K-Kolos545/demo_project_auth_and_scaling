package com.example.demo_project_auth_and_scaling.log;


import com.example.demo_project_auth_and_scaling.user.AppUser;
import com.example.demo_project_auth_and_scaling.user.AuthProvider;
import org.springframework.stereotype.Service;

//import java.security.AuthProvider;
import java.time.LocalDateTime;

@Service
public class LoginEventService {

    private final LoginEventRepository repository;

    public LoginEventService(LoginEventRepository repository) {
        this.repository = repository;
    }

    public void saveEvent(
            AppUser user,
            String email,
            boolean success,
            AuthProvider method,
            String ipAddress,
            String errorMessage
    ) {
        LoginEvent event = LoginEvent.builder()
                .user(user)
                .email(email)
                .success(success)
                .authMethod(method)
                .ipAddress(ipAddress)
                .errorMessage(errorMessage)
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(event);
    }
}

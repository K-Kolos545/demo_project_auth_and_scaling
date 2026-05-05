package com.example.demo_project_auth_and_scaling.analytics;


import com.example.demo_project_auth_and_scaling.log.LoginEvent;
import com.example.demo_project_auth_and_scaling.log.LoginEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyticsService {

    private final LoginEventRepository repository;

    public AnalyticsService(LoginEventRepository repository) {
        this.repository = repository;
    }

    public LoginSummaryResponse getSummary() {
        return new LoginSummaryResponse(
                repository.countBySuccessTrue(),
                repository.countBySuccessFalse()
        );
    }

    public List<LoginEvent> getFailedLogins() {
        return repository.findBySuccessFalseOrderByCreatedAtDesc();
    }

    public List<SuspiciousUserResponse> getSuspiciousUsers() {
        return repository.findSuspiciousUsers()
                .stream()
                .map(email -> new SuspiciousUserResponse(
                        email,
                        "At least 3 failed login attempts"
                ))
                .toList();
    }
}
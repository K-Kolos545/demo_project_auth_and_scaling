package com.example.demo_project_auth_and_scaling.analytics;


import com.example.demo_project_auth_and_scaling.log.LoginEvent;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    private final AnalyticsService service;

    public AnalyticsController(AnalyticsService service) {
        this.service = service;
    }

    @GetMapping("/login-summary")
    public LoginSummaryResponse summary() {
        return service.getSummary();
    }

    @GetMapping("/failed-logins")
    public List<LoginEvent> failedLogins() {
        return service.getFailedLogins();
    }

    @GetMapping("/suspicious-users")
    public List<SuspiciousUserResponse> suspiciousUsers() {
        return service.getSuspiciousUsers();
    }
}

package com.example.outside_provider.service;



import com.example.outside_provider.dto.ExternalAuthRequest;
import com.example.outside_provider.dto.ExternalAuthResponse;
import com.example.outside_provider.model.ExternalUser;
import com.example.outside_provider.repository.ExternalUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ExternalAuthService {

    private final ExternalUserRepository repository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ExternalAuthService(ExternalUserRepository repository) {
        this.repository = repository;
    }

    public ExternalAuthResponse authenticate(ExternalAuthRequest request) {
        ExternalUser user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("External user not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid external credentials");
        }

        return new ExternalAuthResponse(
                user.getEmail(),
                user.getInstitution(),
                user.getEduPersonPrincipalName(),
                "MOCK_EDUID"
        );
    }
}

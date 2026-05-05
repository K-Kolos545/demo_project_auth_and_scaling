package com.example.outside_provider.seed;

import com.example.outside_provider.model.ExternalUser;
import com.example.outside_provider.repository.ExternalUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedExternalUsers(ExternalUserRepository repository) {
        return args -> {
            if (!repository.existsByEmail("student3@elte.hu")) {
                repository.save(
                        ExternalUser.builder()
                                .email("student3@elte.hu")
                                .password(new BCryptPasswordEncoder().encode("password123456"))
                                .institution("ELTE")
                                .eduPersonPrincipalName("student@elte.hu")
                                .build()
                );
            }

            if (!repository.existsByEmail("student@bme.hu")) {
                repository.save(
                        ExternalUser.builder()
                                .email("student@bme.hu")
                                .password(new BCryptPasswordEncoder().encode("password123"))
                                .institution("BME")
                                .eduPersonPrincipalName("student@bme.hu")
                                .build()
                );
            }
        };
    }
}

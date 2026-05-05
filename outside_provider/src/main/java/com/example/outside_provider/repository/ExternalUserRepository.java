package com.example.outside_provider.repository;

import com.example.outside_provider.model.ExternalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExternalUserRepository extends JpaRepository<ExternalUser, Long> {
    Optional<ExternalUser> findByEmail(String email);
    boolean existsByEmail(String email);
}

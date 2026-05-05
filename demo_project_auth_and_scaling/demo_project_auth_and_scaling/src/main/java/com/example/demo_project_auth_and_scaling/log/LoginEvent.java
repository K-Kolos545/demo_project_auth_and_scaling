package com.example.demo_project_auth_and_scaling.log;

import com.example.demo_project_auth_and_scaling.user.AppUser;
import com.example.demo_project_auth_and_scaling.user.AuthProvider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Builder

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "login_events")
public class LoginEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @Column(nullable = false)
    private String email;

    private boolean success;

    @Enumerated(EnumType.STRING)
    private AuthProvider authMethod;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

//    public LoginEvent() {}
//
//    a builder-hez kell, es akkor kene ha nem lenne a @NoArgsConstructor
}

package com.example.outside_provider.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "external_users",
        indexes = {
                @Index(name = "idx_external_user_email", columnList = "email")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String institution;

    @Column(name = "edu_person_principal_name")
    private String eduPersonPrincipalName;
}

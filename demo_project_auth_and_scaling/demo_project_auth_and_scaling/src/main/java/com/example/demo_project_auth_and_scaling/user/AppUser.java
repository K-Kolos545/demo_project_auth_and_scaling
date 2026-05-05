package com.example.demo_project_auth_and_scaling.user;

import com.example.demo_project_auth_and_scaling.log.LoginEvent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users",
        indexes = {
                @Index(name = "idx_user_email", columnList = "email")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder //objektum létrehozását teszi konnyebbe. Letherohz egy Java objektumot
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String institution;

    @Column(name = "edu_person_principal_name")
    private String eduPersonPrincipalName;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<LoginEvent> loginEvents;

}

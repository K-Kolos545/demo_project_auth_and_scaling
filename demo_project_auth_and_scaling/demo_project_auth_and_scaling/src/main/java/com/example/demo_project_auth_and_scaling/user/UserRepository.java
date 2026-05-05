package com.example.demo_project_auth_and_scaling.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <AppUser, Long>{

    Optional<AppUser> findByEmail(String email);
    boolean existsByEmail(String email);


    //todo a felhasznalo loginjeit query-vel

}

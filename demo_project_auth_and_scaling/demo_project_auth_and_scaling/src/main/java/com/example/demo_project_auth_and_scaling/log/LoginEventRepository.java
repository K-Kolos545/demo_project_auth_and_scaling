package com.example.demo_project_auth_and_scaling.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface LoginEventRepository extends JpaRepository <LoginEvent, Long> {
    List<LoginEvent> findBySuccessFalseOrderByCreatedAtDesc();

    long countBySuccessTrue(); //sikeres logineket szamolja

    long countBySuccessFalse(); //sikertelen logineket szamolja

    @Query("""
        select e.email
        From LoginEvent e
        Where e.success = false
        group by e.email
        having COUNT (e.id) >= 3             
        """)
    List<String> findSuspiciousUsers()
;
}

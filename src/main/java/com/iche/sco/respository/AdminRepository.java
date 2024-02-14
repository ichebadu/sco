package com.iche.sco.respository;

import com.iche.sco.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);
}

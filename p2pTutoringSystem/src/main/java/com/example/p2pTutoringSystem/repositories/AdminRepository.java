package com.example.p2pTutoringSystem.repositories;

import com.example.p2pTutoringSystem.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByAdminId(long adminId);
    Optional<Admin> findByEmail(String email);
}

package com.project.backend.repository;

import com.project.backend.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, String> {
    Optional<Admin> findByIdAndPassword(String id, String password);
}

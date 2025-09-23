package com.project.backend.repository;

import com.project.backend.domain.SafetyTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SafetyTestRepository extends JpaRepository<SafetyTest, Integer> {
    Optional<SafetyTest> findByUserId(Integer userId);
}

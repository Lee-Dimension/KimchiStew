package com.project.backend.repository;

import com.project.backend.domain.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EducationRepository extends JpaRepository<Education, Integer> {
    Optional<Education> findByUserId(Integer userId);
}

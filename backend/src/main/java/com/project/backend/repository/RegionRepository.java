package com.project.backend.repository;

import com.project.backend.domain.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, Integer> {

    @Query("SELECT r FROM Region r LEFT JOIN FETCH r.sites s ORDER BY r.name, s.name")
    List<Region> findAllWithSites();

    boolean existsByName(String name);
}

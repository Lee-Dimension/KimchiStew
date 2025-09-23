package com.project.backend.repository;

import com.project.backend.domain.Site;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SiteRepository extends JpaRepository<Site, Integer> {
    Optional<Site> findByNameAndRegionName(String siteName, String regionName);

    boolean existsByNameAndRegionId(String name, Integer regionId);
}

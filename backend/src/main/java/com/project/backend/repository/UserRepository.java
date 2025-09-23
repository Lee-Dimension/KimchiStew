package com.project.backend.repository;

import com.project.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByNameAndPhoneAndSiteId(String name, String phone, Integer siteId);

    @Query("SELECT u FROM User u JOIN FETCH u.site s JOIN FETCH s.region r WHERE r.id = :regionId ORDER BY u.name")
    List<User> findBySiteRegionId(@Param("regionId") Integer regionId);

    @Modifying
    @Transactional
    void deleteBySiteId(Integer siteId);
}

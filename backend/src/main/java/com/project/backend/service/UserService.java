package com.project.backend.service;

import com.project.backend.domain.Site;
import com.project.backend.domain.User;
import com.project.backend.dto.LoginRequestDto;
import com.project.backend.dto.UserListDto;
import com.project.backend.repository.SiteRepository;
import com.project.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final SiteRepository siteRepository;

    public User login(LoginRequestDto requestDto) {
        logger.info("Login attempt with data: {}", requestDto.toString());

        Site site = siteRepository.findByNameAndRegionName(requestDto.site(), requestDto.region())
                .orElseThrow(() -> {
                    logger.warn("Login failed: Site not found for region='{}' and site='{}'", requestDto.region(), requestDto.site());
                    return new IllegalArgumentException("Invalid region or site.");
                });

        logger.info("Site found: '{}' with ID: {}. Searching for user now.", site.getName(), site.getId());

        return userRepository.findByNameAndPhoneAndSiteId(requestDto.name(), requestDto.phone(), site.getId())
                .orElseThrow(() -> {
                    logger.warn("Login failed: User not found with name='{}', phone='{}', siteId='{}'", requestDto.name(), requestDto.phone(), site.getId());
                    return new IllegalArgumentException("Invalid user credentials.");
                });
    }

    public List<UserListDto> getUsersByRegion(Integer regionId) {
        return userRepository.findBySiteRegionId(regionId).stream()
                .map(UserListDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void removeUserLocation(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        // Remove site association from User
        user.setSite(null);
        userRepository.save(user);

        // Reset Education information
        educationRepository.findByUserId(userId).ifPresent(education -> {
            education.setCompletionDate(null);
            educationRepository.save(education);
        });

        // Reset SafetyTest information
        safetyTestRepository.findByUserId(userId).ifPresent(safetyTest -> {
            safetyTest.setTestStatus(null);
            safetyTest.setPassDate(null);
            safetyTestRepository.save(safetyTest);
        });
    }
}

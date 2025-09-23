package com.project.backend.service;

import com.project.backend.domain.Education;
import com.project.backend.domain.SafetyTest;
import com.project.backend.dto.StatusResponseDto;
import com.project.backend.repository.EducationRepository;
import com.project.backend.repository.SafetyTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatusService {

    private final EducationRepository educationRepository;
    private final SafetyTestRepository safetyTestRepository;

    public StatusResponseDto getUserStatus(Integer userId) {
        String educationStatus = getEducationCompletionDate(userId);
        String testStatus = getTestStatus(userId);
        return new StatusResponseDto(educationStatus, testStatus);
    }

    private String getEducationCompletionDate(Integer userId) {
        return educationRepository.findByUserId(userId)
                .map(edu -> edu.getCompletionDate() != null ? edu.getCompletionDate().toString() : "none")
                .orElse("none");
    }

    private String getTestStatus(Integer userId) {
        return safetyTestRepository.findByUserId(userId)
                .map(test -> test.getTestStatus() != null ? test.getTestStatus() : "none")
                .orElse("none");
    }
}

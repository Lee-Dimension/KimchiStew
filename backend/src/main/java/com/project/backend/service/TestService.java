package com.project.backend.service;

import com.project.backend.domain.SafetyTest;
import com.project.backend.domain.User;
import com.project.backend.repository.SafetyTestRepository;
import com.project.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TestService {

    private final SafetyTestRepository safetyTestRepository;
    private final UserRepository userRepository;

    @Transactional
    public void updateTestResult(Integer userId, String testStatus, LocalDate passDate) {
        SafetyTest safetyTest = safetyTestRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
                    SafetyTest newTest = new SafetyTest();
                    newTest.setUser(user);
                    return newTest;
                });

        safetyTest.setTestStatus(testStatus);
        safetyTest.setPassDate(passDate);
        safetyTestRepository.save(safetyTest);
    }
}

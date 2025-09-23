package com.project.backend.service;

import com.project.backend.domain.Education;
import com.project.backend.domain.User;
import com.project.backend.repository.EducationRepository;
import com.project.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class EducationService {

    private final EducationRepository educationRepository;
    private final UserRepository userRepository;

    @Transactional
    public void updateCompletionDate(Integer userId, LocalDate newDate) {
        Education education = educationRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
                    Education newEducation = new Education();
                    newEducation.setUser(user);
                    return newEducation;
                });

        education.setCompletionDate(newDate);
        educationRepository.save(education);
    }
}

package com.project.backend.service;

import com.project.backend.domain.Admin;
import com.project.backend.dto.AdminLoginRequestDto;
import com.project.backend.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final AdminRepository adminRepository;

    public Admin login(AdminLoginRequestDto requestDto) {
        return adminRepository.findByIdAndPassword(requestDto.id(), requestDto.password())
                .orElseThrow(() -> new IllegalArgumentException("Invalid admin credentials."));
    }
}

package com.project.backend.controller;

import com.project.backend.domain.Admin;
import com.project.backend.dto.AdminDto;
import com.project.backend.dto.AdminLoginRequestDto;
import com.project.backend.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdminLoginRequestDto requestDto, HttpServletRequest request) {
        try {
            Admin admin = adminService.login(requestDto);
            AdminDto adminDto = AdminDto.fromEntity(admin);

            HttpSession session = request.getSession(true);
            session.setAttribute("loggedInAdmin", adminDto);

            return ResponseEntity.ok(adminDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}

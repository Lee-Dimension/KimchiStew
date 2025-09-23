package com.project.backend.controller;

import com.project.backend.domain.User;
import com.project.backend.dto.LoginRequestDto;
import com.project.backend.dto.UserDto;
import com.project.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.backend.dto.StatusResponseDto;
import com.project.backend.service.StatusService;
import com.project.backend.dto.UpdateEducationRequestDto;
import com.project.backend.service.EducationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.project.backend.dto.UpdateTestRequestDto;
import com.project.backend.service.TestService;
import com.project.backend.dto.UserListDto;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final StatusService statusService;
    private final EducationService educationService;
    private final TestService testService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto, HttpServletRequest request) {
        try {
            User user = userService.login(requestDto);
            UserDto userDto = UserDto.fromEntity(user);

            // Create new session
            HttpSession session = request.getSession(true);
            session.setAttribute("loggedInUser", userDto);

            return ResponseEntity.ok(userDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/status")
    public ResponseEntity<?> getStatus(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // false: do not create a new session
        if (session == null || session.getAttribute("loggedInUser") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in.");
        }

        UserDto userDto = (UserDto) session.getAttribute("loggedInUser");
        StatusResponseDto status = statusService.getUserStatus(userDto.id());

        return ResponseEntity.ok(status);
    }

    @PutMapping("/education")
    public ResponseEntity<?> updateEducationDate(@RequestBody UpdateEducationRequestDto requestDto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in.");
        }

        UserDto userDto = (UserDto) session.getAttribute("loggedInUser");
        educationService.updateCompletionDate(userDto.id(), requestDto.completionDate());

        return ResponseEntity.ok().body("Education completion date updated successfully.");
    }

    @PutMapping("/test")
    public ResponseEntity<?> updateTestResult(@RequestBody UpdateTestRequestDto requestDto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in.");
        }

        UserDto userDto = (UserDto) session.getAttribute("loggedInUser");
        testService.updateTestResult(userDto.id(), requestDto.testStatus(), requestDto.passDate());

        return ResponseEntity.ok().body("Test result updated successfully.");
    }

    @GetMapping
    public ResponseEntity<List<UserListDto>> getUsersByRegion(@RequestParam Integer regionId) {
        List<UserListDto> users = userService.getUsersByRegion(regionId);
        return ResponseEntity.ok(users);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<?> removeUserLocation(@PathVariable Integer userId) {
        userService.removeUserLocation(userId);
        return ResponseEntity.ok().body("User location removed successfully.");
    }
}

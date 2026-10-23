package com.jayasuriya.studytracker.controller;

import com.jayasuriya.studytracker.dto.Dtos.*;
import com.jayasuriya.studytracker.model.User;
import com.jayasuriya.studytracker.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "User registration, login, and profile management")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new local user and initialize personal 52-week roadmap")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest req, HttpServletResponse response) {
        AuthResponse res = authService.register(req);
        attachHttpOnlyCookie(response, res.token());
        return ResponseEntity.ok(res);
    }

    @PostMapping("/login")
    @Operation(summary = "Login with username or email")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req, HttpServletResponse response) {
        AuthResponse res = authService.login(req);
        attachHttpOnlyCookie(response, res.token());
        return ResponseEntity.ok(res);
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout user and clear auth cookie")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("studytracker_jwt", "");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    @Operation(summary = "Get current authenticated user profile")
    public ResponseEntity<UserDto> getMe(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(authService.getCurrentUserDto(user));
    }

    @PutMapping("/profile")
    @Operation(summary = "Update current user profile and study settings")
    public ResponseEntity<UserDto> updateProfile(@AuthenticationPrincipal User user, @RequestBody UpdateProfileRequest req) {
        return ResponseEntity.ok(authService.updateProfile(user, req));
    }

    private void attachHttpOnlyCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("studytracker_jwt", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(86400); // 24 hours
        response.addCookie(cookie);
    }
}

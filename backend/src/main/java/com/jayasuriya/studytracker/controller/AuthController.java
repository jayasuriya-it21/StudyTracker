package com.jayasuriya.studytracker.controller;

import com.jayasuriya.studytracker.dto.Dtos.*;
import com.jayasuriya.studytracker.model.User;
import com.jayasuriya.studytracker.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "User registration, login, and profile management with HTTP-Only cookie security")
public class AuthController {

    private final AuthService authService;

    @Value("${app.security.cookie.secure:false}")
    private boolean cookieSecure;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new local user and initialize personal 52-week roadmap (sets HTTP-Only cookie)")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest req,
            HttpServletRequest request,
            HttpServletResponse response) {
        AuthService.LoginResult res = authService.register(req);
        attachHttpOnlyCookie(request, response, res.token());
        return ResponseEntity.ok(new AuthResponse(res.user(), "Registration successful"));
    }

    @PostMapping("/login")
    @Operation(summary = "Login with username or email (sets HTTP-Only cookie)")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest req,
            HttpServletRequest request,
            HttpServletResponse response) {
        AuthService.LoginResult res = authService.login(req);
        attachHttpOnlyCookie(request, response, res.token());
        return ResponseEntity.ok(new AuthResponse(res.user(), "Login successful"));
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout user and clear auth cookie")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        boolean isSecure = cookieSecure || request.isSecure();
        ResponseCookie cookie = ResponseCookie.from("studytracker_jwt", "")
                .httpOnly(true)
                .secure(isSecure)
                .path("/")
                .maxAge(Duration.ZERO)
                .sameSite("Strict")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    @Operation(summary = "Get current authenticated user profile from HTTP-Only cookie session")
    public ResponseEntity<UserDto> getMe(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(authService.getCurrentUserDto(user));
    }

    @PutMapping("/profile")
    @Operation(summary = "Update current user profile and study settings")
    public ResponseEntity<UserDto> updateProfile(@AuthenticationPrincipal User user, @RequestBody UpdateProfileRequest req) {
        return ResponseEntity.ok(authService.updateProfile(user, req));
    }

    private void attachHttpOnlyCookie(HttpServletRequest request, HttpServletResponse response, String token) {
        boolean isSecure = cookieSecure || request.isSecure();
        ResponseCookie cookie = ResponseCookie.from("studytracker_jwt", token)
                .httpOnly(true)
                .secure(isSecure)
                .path("/")
                .maxAge(Duration.ofDays(7))
                .sameSite("Strict")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}

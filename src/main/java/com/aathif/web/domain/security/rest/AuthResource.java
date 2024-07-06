package com.aathif.web.domain.security.rest;

import com.aathif.web.domain.security.dto.AuthResponseDTO;
import com.aathif.web.domain.security.dto.LogInDTO;
import com.aathif.web.domain.security.dto.ResetForgotPasswordDTO;
import com.aathif.web.domain.security.service.AuthService;
import com.aathif.web.domain.user.ResetPasswordDTO;
import com.aathif.web.dto.ApplicationResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthResource {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LogInDTO logInDTO) {
        return ResponseEntity.ok(authService.login(logInDTO));
    }

    @PostMapping("/forgot-password/{email}")
    public ResponseEntity<ApplicationResponseDTO> forgotPassword(@PathVariable("email") String email, HttpServletRequest request) {
        return ResponseEntity.ok(authService.forgotPassword(email, request));
    }

    @GetMapping("/reset-password/{id}")
    public ResponseEntity<ApplicationResponseDTO> resetForgotPassword(@PathVariable("id") Long id) {
        return ResponseEntity.ok(authService.resetForgotPassword(id));
    }

    @PostMapping("/reset-password/{id}")
    public ResponseEntity<ApplicationResponseDTO> resetForgotPassword(@PathVariable("id") Long id, @Valid @RequestBody ResetForgotPasswordDTO resetForgotPasswordDTO) {
        return ResponseEntity.ok(authService.resetForgotPassword(id, resetForgotPasswordDTO));
    }

    @PostMapping("/token/refresh/{refresh-token}")
    public ResponseEntity<AuthResponseDTO> refreshAccessToken(@PathVariable("refresh-token") String refreshToken) {
        return ResponseEntity.ok(authService.generateRefreshToken(refreshToken));
    }

    @PutMapping("/reset-password")
    public ResponseEntity<ApplicationResponseDTO> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO) {
        return ResponseEntity.ok(authService.resetPassword(resetPasswordDTO));
    }

}

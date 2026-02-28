package com.seo2yeon.students.domain.auth.controller;

import com.seo2yeon.students.domain.auth.dto.EmailSendRequest;
import com.seo2yeon.students.domain.auth.dto.EmailVerifyRequest;
import com.seo2yeon.students.domain.auth.dto.LoginRequest;
import com.seo2yeon.students.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/email/send")
    public ResponseEntity<String> sendEmail(@Valid @RequestBody EmailSendRequest request) {
        authService.sendVerificationCode(request.getEmail());
        return ResponseEntity.ok("인증 번호가 발송되었습니다.");
    }

    @PostMapping("/email/verify")
    public ResponseEntity<String> verifyEmail(@Valid @RequestBody EmailVerifyRequest request) {
        authService.verifyEmailCode(request.getEmail(), request.getCode());
        return ResponseEntity.ok("이메일 인증 완료");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        String token = authService.login(
                request.getEmail(),
                request.getPassword()
        );

        return ResponseEntity.ok(token);
    }
}

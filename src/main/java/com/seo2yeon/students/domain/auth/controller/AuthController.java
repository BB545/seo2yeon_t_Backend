package com.seo2yeon.students.domain.auth.controller;

import com.seo2yeon.students.domain.auth.dto.EmailSendRequest;
import com.seo2yeon.students.domain.auth.dto.EmailVerifyRequest;
import com.seo2yeon.students.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
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
    public String sendEmail(@RequestBody EmailSendRequest request) {
        authService.sendVerificationCode(request.getEmail());
        return "인증 번호가 발송되었습니다.";
    }

    @PostMapping("/email/verify")
    public String verifyEmail(@RequestBody EmailVerifyRequest request) {
        authService.verifyEmailCode(request.getEmail(), request.getCode());
        return "이메일 인증 완료";
    }
}

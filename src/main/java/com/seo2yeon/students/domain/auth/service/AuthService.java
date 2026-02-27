package com.seo2yeon.students.domain.auth.service;

import com.seo2yeon.students.domain.auth.entity.EmailVerification;
import com.seo2yeon.students.domain.auth.repository.EmailVerificationRepository;
import com.seo2yeon.students.global.util.RandomCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final EmailVerificationRepository emailVerificationRepository;

    public void sendVerificationCode(String email) {
        String code = RandomCodeGenerator.generate6DigitCode();

        EmailVerification verification = EmailVerification.builder()
                .email(email)
                .code(code)
                .verified(false)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(3))
                .build();

        emailVerificationRepository.save(verification);

        System.out.println("인증코드: " + code);
    }
}

package com.seo2yeon.students.domain.auth.service;

import com.seo2yeon.students.domain.auth.entity.EmailVerification;
import com.seo2yeon.students.domain.auth.repository.EmailVerificationRepository;
import com.seo2yeon.students.global.exception.CustomException;
import com.seo2yeon.students.global.exception.ErrorCode;
import com.seo2yeon.students.global.util.RandomCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final EmailVerificationRepository emailVerificationRepository;

    public void sendVerificationCode(String email) {
        String code = RandomCodeGenerator.generate6DigitCode();

        EmailVerification verification = emailVerificationRepository.findByEmail(email).orElse(null);

        if (verification == null) {
            verification = EmailVerification.builder()
                    .email(email)
                    .code(code)
                    .verified(false)
                    .createdAt(LocalDateTime.now())
                    .expiredAt(LocalDateTime.now().plusMinutes(3))
                    .build();

            emailVerificationRepository.save(verification);
        } else {
            if (verification.getVerified()) {
                throw new CustomException(ErrorCode.EMAIL_ALREADY_VERIFIED);
            }

            verification.updateCode(code, LocalDateTime.now().plusMinutes(3));
        }

        System.out.println("인증코드: " + code);
    }

    public void verifyEmailCode(String email, String inputCode) {
        EmailVerification verification = emailVerificationRepository
                .findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.EMAIL_NOT_FOUND));

        if (verification.getVerified()) {
            throw new CustomException(ErrorCode.EMAIL_ALREADY_VERIFIED);
        }

        if (verification.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.EMAIL_EXPIRED);
        }

        if (!verification.getCode().equals(inputCode)) {
            throw new CustomException(ErrorCode.EMAIL_CODE_MISMATCH);
        }

        verification.verify();
    }
}

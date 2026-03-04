package com.seo2yeon.students.domain.auth.service;

import com.seo2yeon.students.domain.auth.entity.EmailVerification;
import com.seo2yeon.students.domain.auth.repository.EmailVerificationRepository;
import com.seo2yeon.students.domain.user.entity.User;
import com.seo2yeon.students.domain.user.repository.UserRepository;
import com.seo2yeon.students.global.exception.CustomException;
import com.seo2yeon.students.global.jwt.JwtTokenProvider;
import com.seo2yeon.students.global.mail.EmailService;
import com.seo2yeon.students.global.util.RandomCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final EmailVerificationRepository emailVerificationRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    private static final long COOLDOWN_SECONDS = 60;

    public void sendVerificationCode(String email) {
        String code = RandomCodeGenerator.generate6DigitCode();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredAt = now.plusMinutes(3);

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
            if (verification.getCreatedAt()
                    .plusSeconds(COOLDOWN_SECONDS)
                    .isAfter(now)) {

                throw new CustomException(ErrorCode.TOO_MANY_REQUESTS);
            }

            verification.updateCode(code, expiredAt);
        }

        emailService.sendVerificationEmail(email, code);
    }

    public void verifyEmailCode(String email, String inputCode) {
        EmailVerification verification = emailVerificationRepository
                .findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.EMAIL_NOT_FOUND));

        if (verification.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.EMAIL_EXPIRED);
        }

        if (!verification.getCode().equals(inputCode)) {
            throw new CustomException(ErrorCode.EMAIL_CODE_MISMATCH);
        }

        verification.verify();
    }

    public String login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        return jwtTokenProvider.generateToken(email);
    }
}

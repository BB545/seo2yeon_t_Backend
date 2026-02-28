package com.seo2yeon.students.domain.user.service;

import com.seo2yeon.students.domain.auth.entity.EmailVerification;
import com.seo2yeon.students.domain.auth.repository.EmailVerificationRepository;
import com.seo2yeon.students.domain.auth.dto.SignupRequest;
import com.seo2yeon.students.domain.user.entity.User;
import com.seo2yeon.students.domain.user.repository.UserRepository;
import com.seo2yeon.students.global.exception.CustomException;
import com.seo2yeon.students.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequest request) {
        LocalDateTime now = LocalDateTime.now();

        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            if (!existingUser.getDeleted()) {
                throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
            }

            if (existingUser.getDeletedAt().plusDays(30).isAfter(now)) {
                throw new CustomException(ErrorCode.REJOIN_NOT_ALLOWED);
            }

            existingUser.restore();

            User updatedUser = User.builder()
                    .id(existingUser.getId())
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .phone(request.getPhone())
                    .schoolName(request.getSchoolName())
                    .grade(request.getGrade())
                    .deleted(false)
                    .build();

            userRepository.save(updatedUser);
        } else {
            EmailVerification verification = emailVerificationRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new CustomException(ErrorCode.EMAIL_NOT_FOUND));

            if (!verification.getVerified()) {
                throw new CustomException(ErrorCode.EMAIL_NOT_VERIFIED);
            }

            User user = User.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .phone(request.getPhone())
                    .schoolName(request.getSchoolName())
                    .grade(request.getGrade())
                    .deleted(false)
                    .build();

            userRepository.save(user);
        }

        emailVerificationRepository.findByEmail(request.getEmail())
                .ifPresent(emailVerificationRepository::delete);
    }
}

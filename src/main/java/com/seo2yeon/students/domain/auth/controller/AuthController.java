package com.seo2yeon.students.domain.auth.controller;

import com.seo2yeon.students.domain.auth.dto.EmailSendRequest;
import com.seo2yeon.students.domain.auth.dto.EmailVerifyRequest;
import com.seo2yeon.students.domain.auth.dto.LoginRequest;
import com.seo2yeon.students.domain.auth.service.AuthService;
import com.seo2yeon.students.domain.auth.dto.SignupRequest;
import com.seo2yeon.students.domain.user.service.UserService;
import com.seo2yeon.students.global.response.BaseResponse;
import com.seo2yeon.students.global.response.SuccessCode;
import jakarta.validation.Valid;
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
    private final UserService userService;

    @PostMapping("/signup")
    public BaseResponse<String> signup(@Valid @RequestBody SignupRequest request) {
        userService.signup(request);
        return new BaseResponse<>(
                SuccessCode.SIGNUP_SUCCESS,
                "회원가입이 완료되었습니다."
        );
    }

    @PostMapping("/email/send")
    public BaseResponse<String> sendEmail(@Valid @RequestBody EmailSendRequest request) {
        authService.sendVerificationCode(request.getEmail());
        return new BaseResponse<>(
                SuccessCode.EMAIL_SEND_SUCCESS,
                "인증번호가 이메일로 발송되었습니다."
        );
    }

    @PostMapping("/email/verify")
    public BaseResponse<String> verifyEmail(@Valid @RequestBody EmailVerifyRequest request) {
        authService.verifyEmailCode(request.getEmail(), request.getCode());
        return new BaseResponse<>(
                SuccessCode.EMAIL_VERIFY_SUCCESS,
                "이메일 인증이 완료되었습니다."
        );
    }

    @PostMapping("/login")
    public BaseResponse<String> login(@RequestBody LoginRequest request) {

        String token = authService.login(
                request.getEmail(),
                request.getPassword()
        );

        return new BaseResponse<>(SuccessCode.LOGIN_SUCCESS, "Bearer " + token);
    }
}

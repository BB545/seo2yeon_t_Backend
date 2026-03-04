package com.seo2yeon.students.global.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SuccessCode {
    LOGIN_SUCCESS(HttpStatus.OK, "AUTH200", "로그인 성공"),
    SIGNUP_SUCCESS(HttpStatus.OK, "AUTH201", "회원가입 성공"),
    EMAIL_SEND_SUCCESS(HttpStatus.OK, "AUTH202", "인증번호 발송 성공"),
    EMAIL_VERIFY_SUCCESS(HttpStatus.OK, "AUTH203", "이메일 인증 성공"),
    SCHOOL_SEARCH_SUCCESS(HttpStatus.OK, "SCHOOL200", "학교 검색 성공");

    private final HttpStatus status;
    private final String code;
    private final String message;

    SuccessCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}

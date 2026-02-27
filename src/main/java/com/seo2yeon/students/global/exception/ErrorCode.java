package com.seo2yeon.students.global.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    EMAIL_NOT_FOUND(HttpStatus.BAD_REQUEST, "인증 요청이 없습니다."),
    EMAIL_ALREADY_VERIFIED(HttpStatus.BAD_REQUEST, "이미 인증이 완료된 이메일입니다."),
    EMAIL_EXPIRED(HttpStatus.BAD_REQUEST, "인증 번호가 만료되었습니다."),
    EMAIL_CODE_MISMATCH(HttpStatus.BAD_REQUEST, "인증 번호가 일치하지 않습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}

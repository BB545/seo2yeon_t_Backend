package com.seo2yeon.students.global.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    TOO_MANY_REQUESTS(HttpStatus.TOO_MANY_REQUESTS, "인증 요청은 1분에 한 번만 가능합니다."),
    REJOIN_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "탈퇴 후 30일 이내에는 재가입이 불가합니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 가입된 이메일입니다."),
    EMAIL_NOT_VERIFIED(HttpStatus.BAD_REQUEST, "이메일 인증이 완료되지 않았습니다."),
    EMAIL_EXPIRED(HttpStatus.BAD_REQUEST, "인증이 만료되었습니다."),
    EMAIL_CODE_MISMATCH(HttpStatus.BAD_REQUEST, "인증 번호가 일치하지 않습니다."),
    EMAIL_NOT_FOUND(HttpStatus.BAD_REQUEST, "인증 요청이 없습니다.");

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

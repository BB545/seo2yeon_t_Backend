package com.seo2yeon.students.global.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    TOO_MANY_REQUESTS(HttpStatus.TOO_MANY_REQUESTS, "AUTH429", "인증 요청은 1분에 한 번만 가능합니다."),
    REJOIN_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "AUTH403", "탈퇴 후 30일 이내에는 재가입이 불가합니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "AUTH409", "이미 가입된 이메일입니다."),
    EMAIL_NOT_VERIFIED(HttpStatus.BAD_REQUEST, "AUTH401", "이메일 인증이 완료되지 않았습니다."),
    EMAIL_EXPIRED(HttpStatus.BAD_REQUEST, "AUTH402", "인증이 만료되었습니다."),
    EMAIL_CODE_MISMATCH(HttpStatus.BAD_REQUEST, "AUTH404", "인증 번호가 일치하지 않습니다."),
    EMAIL_NOT_FOUND(HttpStatus.BAD_REQUEST, "AUTH405", "인증 요청이 없습니다."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER404", "사용자를 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "AUTH400", "비밀번호가 올바르지 않습니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}

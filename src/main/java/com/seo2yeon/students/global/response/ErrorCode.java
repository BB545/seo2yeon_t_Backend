package com.seo2yeon.students.global.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON404", "존재하지 않는 리소스입니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),

    TOO_MANY_REQUESTS(HttpStatus.TOO_MANY_REQUESTS, "AUTH429", "인증 요청은 1분에 한 번만 가능합니다."),
    REJOIN_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "AUTH403", "탈퇴 후 30일 이내에는 재가입이 불가합니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "AUTH409", "이미 가입된 이메일입니다."),
    EMAIL_NOT_VERIFIED(HttpStatus.BAD_REQUEST, "AUTH401", "이메일 인증이 완료되지 않았습니다."),
    EMAIL_EXPIRED(HttpStatus.BAD_REQUEST, "AUTH402", "인증이 만료되었습니다."),
    EMAIL_CODE_MISMATCH(HttpStatus.BAD_REQUEST, "AUTH404", "인증 번호가 일치하지 않습니다."),
    EMAIL_NOT_FOUND(HttpStatus.BAD_REQUEST, "AUTH405", "인증 요청이 없습니다."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER404", "사용자를 찾을 수 없습니다."),
    USER_DELETED(HttpStatus.FORBIDDEN, "USER403", "탈퇴 처리된 사용자입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "AUTH400", "비밀번호가 올바르지 않습니다."),

    QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "QNA4041", "존재하지 않는 질문입니다."),
    QUESTION_ACCESS_DENIED(HttpStatus.FORBIDDEN, "QNA4031", "해당 질문에 접근할 수 없습니다."),
    QUESTION_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "QNA4001", "이미 삭제된 질문입니다."),

    ANSWER_NOT_FOUND(HttpStatus.NOT_FOUND, "QNA4042", "존재하지 않는 답변입니다."),
    ANSWER_ALREADY_EXISTS(HttpStatus.CONFLICT, "ANSWER409", "이미 답변이 작성된 질문입니다."),

    FILE_COUNT_EXCEEDED(HttpStatus.BAD_REQUEST, "FILE400", "첨부파일은 최대 5개까지 업로드 가능합니다."),
    FILE_SIZE_EXCEEDED(HttpStatus.BAD_REQUEST, "FILE401", "파일 크기는 최대 10MB입니다."),
    INVALID_FILE_TYPE(HttpStatus.BAD_REQUEST, "FILE402", "허용되지 않은 파일 형식입니다."),
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "FILE404", "파일을 찾을 수 없습니다."),
    FILE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "FILE_403", "파일 접근 권한이 없습니다."),

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

package com.seo2yeon.students.global.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SuccessCode {
    LOGIN_SUCCESS(HttpStatus.OK, "AUTH200", "로그인 성공"),
    SIGNUP_SUCCESS(HttpStatus.OK, "AUTH201", "회원가입 성공"),
    EMAIL_SEND_SUCCESS(HttpStatus.OK, "AUTH202", "인증번호 발송 성공"),
    EMAIL_VERIFY_SUCCESS(HttpStatus.OK, "AUTH203", "이메일 인증 성공"),
    SCHOOL_SEARCH_SUCCESS(HttpStatus.OK, "SCHOOL200", "학교 검색 성공"),

    QUESTION_CREATE_SUCCESS(HttpStatus.OK,"QNA2001", "질문 작성 성공"),
    QUESTION_UPDATE_SUCCESS(HttpStatus.OK,"QNA2002", "질문 수정 성공"),
    QUESTION_DELETE_SUCCESS(HttpStatus.OK,"QNA2003", "질문 삭제 성공"),
    QUESTION_GET_SUCCESS(HttpStatus.OK,"QNA2004", "질문 조회 성공"),
    QUESTION_LIST_SUCCESS(HttpStatus.OK,"QNA2005", "질문 목록 조회 성공"),
    QUESTION_DETAIL_SUCCESS(HttpStatus.OK,"QNA_202", "질문 조회 성공"),

    ADMIN_QUESTION_LIST_SUCCESS(HttpStatus.OK,"QNA2101", "관리자 질문 목록 조회 성공"),
    ADMIN_QUESTION_GET_SUCCESS(HttpStatus.OK,"QNA2102", "관리자 질문 상세 조회 성공"),

    ANSWER_CREATE_SUCCESS(HttpStatus.OK,"QNA2201", "답변 작성 성공"),
    ANSWER_UPDATE_SUCCESS(HttpStatus.OK,"QNA2202", "답변 수정 성공"),
    ANSWER_DELETE_SUCCESS(HttpStatus.OK,"QNA2203", "답변 삭제 성공");

    private final HttpStatus status;
    private final String code;
    private final String message;

    SuccessCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}

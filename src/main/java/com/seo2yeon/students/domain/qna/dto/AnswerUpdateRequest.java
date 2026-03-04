package com.seo2yeon.students.domain.qna.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AnswerUpdateRequest {
    @NotBlank(message = "답변 내용을 입력해주세요.")
    private String content;
}

package com.seo2yeon.students.domain.qna.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AnswerResponse {
    private String content;

    private LocalDateTime createdAt;
}

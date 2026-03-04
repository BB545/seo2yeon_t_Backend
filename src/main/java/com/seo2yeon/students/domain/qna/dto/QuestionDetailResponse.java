package com.seo2yeon.students.domain.qna.dto;

import com.seo2yeon.students.domain.qna.entity.QuestionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class QuestionDetailResponse {
    private Long questionId;

    private String title;

    private String content;

    private String writerName;

    private QuestionStatus status;

    private LocalDateTime createdAt;

    private AnswerResponse answer;
}

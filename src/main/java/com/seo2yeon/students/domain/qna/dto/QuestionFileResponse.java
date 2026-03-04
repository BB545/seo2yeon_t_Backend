package com.seo2yeon.students.domain.qna.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestionFileResponse {
    private Long fileId;

    private String fileName;

    private String fileUrl;
}

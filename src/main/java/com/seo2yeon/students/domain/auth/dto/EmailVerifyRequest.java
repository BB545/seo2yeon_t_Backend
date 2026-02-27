package com.seo2yeon.students.domain.auth.dto;

import lombok.Getter;

@Getter
public class EmailVerifyRequest {
    private String email;
    private String code;
}

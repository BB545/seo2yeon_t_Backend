package com.seo2yeon.students.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EmailVerifyRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String code;
}

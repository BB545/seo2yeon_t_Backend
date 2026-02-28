package com.seo2yeon.students.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EmailSendRequest {
    @Email
    @NotBlank
    private String email;
}

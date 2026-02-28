package com.seo2yeon.students.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupRequest {
    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*[^A-Za-z0-9]).{8,}$",
            message = "비밀번호는 8자 이상이며 영문과 특수문자를 포함해야 합니다."
    )
    private String password;

    @NotBlank
    private String phone;

    private String schoolName;
    private Integer grade;
}

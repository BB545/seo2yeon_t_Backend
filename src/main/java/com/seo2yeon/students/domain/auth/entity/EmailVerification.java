package com.seo2yeon.students.domain.auth.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "email_verifications")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EmailVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private Boolean verified;

    private LocalDateTime expiredAt;
    private LocalDateTime createdAt;

    public void verify() {
        this.verified = true;
    }

    public void updateCode(String code, LocalDateTime expiredAt) {
        this.code = code;
        this.expiredAt = expiredAt;
        this.createdAt = LocalDateTime.now();
        this.verified = false;
    }
}

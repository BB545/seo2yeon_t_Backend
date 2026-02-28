package com.seo2yeon.students.domain.school.controller;

import com.seo2yeon.students.domain.school.service.SchoolService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schools")
@RequiredArgsConstructor
public class SchoolController {
    private final SchoolService schoolService;

    @GetMapping
    public ResponseEntity<String> searchSchool(
            @RequestParam
            @NotBlank(message = "지역을 선택해주세요.")
            String locationName,
            @RequestParam
            @NotBlank(message = "학교명을 입력해주세요.")
            String keyword
    ) {
        return ResponseEntity.ok(
                schoolService.search(locationName, keyword)
        );
    }
}

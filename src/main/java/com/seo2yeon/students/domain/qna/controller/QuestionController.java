package com.seo2yeon.students.domain.qna.controller;

import com.seo2yeon.students.domain.qna.dto.*;
import com.seo2yeon.students.domain.qna.service.QuestionService;
import com.seo2yeon.students.global.response.BaseResponse;
import com.seo2yeon.students.global.response.SuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse<QuestionCreateResponse> createQuestion(
            @RequestHeader("userId") Long userId,
            @ModelAttribute @Valid QuestionCreateRequest request
    ) {
        QuestionCreateResponse response = questionService.createQuestion(userId, request);

        return new BaseResponse<>(
                SuccessCode.QUESTION_CREATE_SUCCESS,
                response
        );
    }

    @GetMapping
    public BaseResponse<Page<QuestionListResponse>> getQuestions(
            @RequestHeader("userId") Long userId,
            Pageable pageable
    ) {
        Page<QuestionListResponse> result = questionService.getQuestionListForStudent(userId, pageable);

        return new BaseResponse<>(
                SuccessCode.QUESTION_LIST_SUCCESS,
                result
        );
    }

    @GetMapping("/admin")
    public BaseResponse<Page<QuestionListResponse>> getQuestionsForAdmin(
            @RequestHeader("userId") Long userId,
            Pageable pageable
    ) {
        Page<QuestionListResponse> result = questionService.getQuestionListForAdmin(userId, pageable);

        return new BaseResponse<>(
                SuccessCode.QUESTION_LIST_SUCCESS,
                result
        );
    }

    @GetMapping("/{questionId}")
    public BaseResponse<QuestionDetailResponse> getQuestionDetail(
            @RequestHeader("userId") Long userId,
            @PathVariable Long questionId
    ) {
        QuestionDetailResponse result =
                questionService.getQuestionDetail(userId, questionId);

        return new BaseResponse<>(
                SuccessCode.QUESTION_DETAIL_SUCCESS,
                result
        );
    }

    @PutMapping(value = "/{questionId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse<QuestionUpdateResponse> updateQuestion(
            @RequestHeader("userId") Long userId,
            @PathVariable Long questionId,
            @ModelAttribute @Valid QuestionUpdateRequest request
    ) {

        QuestionUpdateResponse result =
                questionService.updateQuestion(userId, questionId, request);

        return new BaseResponse<>(
                SuccessCode.QUESTION_UPDATE_SUCCESS,
                result
        );
    }
}

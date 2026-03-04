package com.seo2yeon.students.domain.qna.controller;

import com.seo2yeon.students.domain.qna.dto.AnswerCreateRequest;
import com.seo2yeon.students.domain.qna.dto.AnswerUpdateRequest;
import com.seo2yeon.students.domain.qna.service.AnswerService;
import com.seo2yeon.students.global.response.BaseResponse;
import com.seo2yeon.students.global.response.SuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/answers")
public class AnswerController {
    private final AnswerService answerService;

    @PostMapping("/{questionId}/answer")
    public BaseResponse<Void> createAnswer(
            @RequestHeader("userId") Long userId,
            @PathVariable Long questionId,
            @RequestBody @Valid AnswerCreateRequest request
    ) {
        answerService.createAnswer(userId, questionId, request);

        return new BaseResponse<>(SuccessCode.ANSWER_CREATE_SUCCESS, null);
    }

    @PutMapping("/{answerId}")
    public BaseResponse<Void> updateAnswer(
            @RequestHeader("userId") Long userId,
            @PathVariable Long answerId,
            @RequestBody AnswerUpdateRequest request
    ) {
        answerService.updateAnswer(userId, answerId, request);

        return new BaseResponse<>(SuccessCode.ANSWER_UPDATE_SUCCESS, null);
    }

    @DeleteMapping("/{answerId}")
    public BaseResponse<Void> deleteAnswer(
            @RequestHeader("userId") Long userId,
            @PathVariable Long answerId
    ) {
        answerService.deleteAnswer(userId, answerId);

        return new BaseResponse<>(SuccessCode.ANSWER_DELETE_SUCCESS, null);
    }
}

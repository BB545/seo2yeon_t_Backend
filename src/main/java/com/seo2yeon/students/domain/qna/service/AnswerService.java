package com.seo2yeon.students.domain.qna.service;

import com.seo2yeon.students.domain.qna.dto.AnswerCreateRequest;
import com.seo2yeon.students.domain.qna.dto.AnswerCreateResponse;
import com.seo2yeon.students.domain.qna.dto.AnswerUpdateRequest;
import com.seo2yeon.students.domain.qna.entity.Answer;
import com.seo2yeon.students.domain.qna.entity.Question;
import com.seo2yeon.students.domain.qna.entity.QuestionStatus;
import com.seo2yeon.students.domain.qna.repository.AnswerRepository;
import com.seo2yeon.students.domain.qna.repository.QuestionRepository;
import com.seo2yeon.students.domain.user.entity.User;
import com.seo2yeon.students.domain.user.repository.UserRepository;
import com.seo2yeon.students.global.exception.CustomException;
import com.seo2yeon.students.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public AnswerCreateResponse createAnswer(Long adminId, Long questionId, AnswerCreateRequest request) {

        User admin = getUserOrThrow(adminId);

        if (!admin.isAdmin()) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        Question question = getQuestionOrThrow(questionId);

        answerRepository.findByQuestionIdAndDeletedAtIsNull(questionId)
                .ifPresent(a -> {
                    throw new CustomException(ErrorCode.ANSWER_ALREADY_EXISTS);
                });

        Answer answer = Answer.builder()
                .questionId(questionId)
                .adminId(adminId)
                .content(request.getContent())
                .build();

        Answer savedAnswer = answerRepository.save(answer);

        question.updateStatus(QuestionStatus.ANSWERED);

        return new AnswerCreateResponse(savedAnswer.getId());
    }

    @Transactional
    public void updateAnswer(Long userId, Long answerId, AnswerUpdateRequest request) {
        User user = getUserOrThrow(userId);

        if (!user.isAdmin()) {
            throw new CustomException(ErrorCode.ADMIN_PERMISSION_REQUIRED);
        }

        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new CustomException(ErrorCode.ANSWER_NOT_FOUND));

        answer.updateContent(request.getContent());
    }

    @Transactional
    public void deleteAnswer(Long userId, Long answerId) {
        User user = getUserOrThrow(userId);

        if (!user.isAdmin()) {
            throw new CustomException(ErrorCode.ADMIN_PERMISSION_REQUIRED);
        }

        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new CustomException(ErrorCode.ANSWER_NOT_FOUND));

        if (answer.isDeleted()) {
            throw new CustomException(ErrorCode.ANSWER_NOT_FOUND);
        }

        Question question = getQuestionOrThrow(answer.getQuestionId());

        answer.softDelete();

        question.updateStatus(QuestionStatus.WAITING);
    }

    private User getUserOrThrow(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (Boolean.TRUE.equals(user.getDeleted())) {
            throw new CustomException(ErrorCode.USER_DELETED);
        }

        return user;
    }

    private Question getQuestionOrThrow(Long questionId) {

        return questionRepository.findByIdAndDeletedAtIsNull(questionId)
                .orElseThrow(() -> new CustomException(ErrorCode.QUESTION_NOT_FOUND));
    }
}

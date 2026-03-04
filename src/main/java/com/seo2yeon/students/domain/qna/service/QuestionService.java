package com.seo2yeon.students.domain.qna.service;

import com.seo2yeon.students.domain.file.entity.File;
import com.seo2yeon.students.domain.file.repository.FileRepository;
import com.seo2yeon.students.domain.qna.dto.*;
import com.seo2yeon.students.domain.qna.entity.Answer;
import com.seo2yeon.students.domain.qna.entity.Question;
import com.seo2yeon.students.domain.qna.entity.QuestionFile;
import com.seo2yeon.students.domain.qna.entity.QuestionStatus;
import com.seo2yeon.students.domain.qna.repository.AnswerRepository;
import com.seo2yeon.students.domain.qna.repository.QuestionFileRepository;
import com.seo2yeon.students.domain.qna.repository.QuestionRepository;
import com.seo2yeon.students.domain.user.entity.User;
import com.seo2yeon.students.domain.user.repository.UserRepository;
import com.seo2yeon.students.global.exception.CustomException;
import com.seo2yeon.students.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {
    private static final String PRIVATE_TITLE = "비공개 내용입니다.";

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;
    private final FileRepository fileRepository;
    private final QuestionFileRepository questionFileRepository;

    private static final int MAX_FILE_COUNT = 5;

    private static final Set<String> ALLOWED_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "application/pdf"
    );

    @Transactional
    public QuestionCreateResponse createQuestion(Long userId, QuestionCreateRequest request) {
        User user = getUserOrThrow(userId);

        List<MultipartFile> files = request.getFiles();

        // 파일 개수 제한
        if (files != null && files.size() > 5) {
            throw new CustomException(ErrorCode.FILE_COUNT_EXCEEDED);
        }

        Question question = Question.builder()
                .userId(userId)
                .title(request.getTitle())
                .content(request.getContent())
                .status(QuestionStatus.WAITING)
                .build();

        Question savedQuestion = questionRepository.save(question);

        if(files != null && !files.isEmpty()){
            for(MultipartFile file : files){
                validateFile(file);

                String originName = file.getOriginalFilename();
                String storedName = UUID.randomUUID() + "_" + originName;

                File savedFile = fileRepository.save(
                        File.builder()
                                .originName(originName)
                                .storedName(storedName)
                                .filePath("/uploads/" + storedName)
                                .fileSize(file.getSize())
                                .mimeType(file.getContentType())
                                .uploadedBy(userId)
                                .build()
                );

                questionFileRepository.save(
                        QuestionFile.builder()
                                .questionId(savedQuestion.getId())
                                .fileId(savedFile.getId())
                                .build()
                );
            }
        }

        return new QuestionCreateResponse(savedQuestion.getId());
    }

    public Page<QuestionListResponse> getQuestionListForStudent(Long userId, Pageable pageable) {
        User viewer = getUserOrThrow(userId);

        Page<Question> page = questionRepository.findAllByDeletedAtIsNull(pageable);

        Set<Long> writerIds = page.getContent().stream()
                .map(Question::getUserId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Map<Long, User> writerMap = userRepository.findAllById(writerIds).stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        return page.map(q -> {
            boolean isMine = Objects.equals(q.getUserId(), viewer.getId());

            User writer = writerMap.get(q.getUserId());
            String writerName = (writer == null) ? "알 수 없음" : maskName(writer.getName());

            String title = isMine ? q.getTitle() : PRIVATE_TITLE;

            return new QuestionListResponse(
                    q.getId(),
                    title,
                    writerName,
                    q.getStatus(),
                    q.getCreatedAt(),
                    isMine
            );
        });
    }

    public Page<QuestionListResponse> getQuestionListForAdmin(Long adminId, Pageable pageable) {
        User admin = getUserOrThrow(adminId);
        if (!admin.isAdmin()) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        Page<Question> page = questionRepository.findAllByDeletedAtIsNull(pageable);

        Set<Long> writerIds = page.getContent().stream()
                .map(Question::getUserId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Map<Long, User> writerMap = userRepository.findAllById(writerIds).stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        return page.map(q -> {
            User writer = writerMap.get(q.getUserId());
            String writerName = (writer == null) ? "알 수 없음" : maskName(writer.getName());

            return new QuestionListResponse(
                    q.getId(),
                    q.getTitle(),
                    writerName,
                    q.getStatus(),
                    q.getCreatedAt(),
                    false
            );
        });
    }

    public QuestionDetailResponse getQuestionDetail(Long userId, Long questionId) {

        Question question = getQuestionOrThrow(questionId);

        User user = getUserOrThrow(userId);

        if (!user.isAdmin() && !question.getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        User writer = getUserOrThrow(question.getUserId());

        AnswerResponse answerResponse = null;

        Answer answer = answerRepository.findByQuestionIdAndDeletedAtIsNull(questionId)
                .orElse(null);

        if (answer != null) {
            answerResponse = new AnswerResponse(
                    answer.getContent(),
                    answer.getCreatedAt()
            );
        }

        return new QuestionDetailResponse(
                question.getId(),
                question.getTitle(),
                question.getContent(),
                writer.getName(),
                question.getStatus(),
                question.getCreatedAt(),
                answerResponse
        );
    }

    private void validateFile(MultipartFile file) {

        if (file.isEmpty()) {
            throw new CustomException(ErrorCode.INVALID_FILE_TYPE);
        }

        String contentType = file.getContentType();

        if (contentType == null || !ALLOWED_TYPES.contains(contentType)) {
            throw new CustomException(ErrorCode.INVALID_FILE_TYPE);
        }
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

    private String maskName(String name) {
        if (name == null || name.isBlank()) return "알 수 없음";
        if (name.length() == 1) return name;
        return name.charAt(0) + "**";
    }
}

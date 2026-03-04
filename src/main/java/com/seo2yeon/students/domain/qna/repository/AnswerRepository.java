package com.seo2yeon.students.domain.qna.repository;

import com.seo2yeon.students.domain.qna.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findByQuestionIdAndDeletedAtIsNull(Long questionId);
    Optional<Answer> findByIdAndDeletedAtIsNull(Long id);
    Optional<Answer> findByQuestionId(Long questionId);
}

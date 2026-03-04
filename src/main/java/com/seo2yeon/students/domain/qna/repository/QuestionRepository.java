package com.seo2yeon.students.domain.qna.repository;

import com.seo2yeon.students.domain.qna.entity.Question;
import com.seo2yeon.students.domain.qna.entity.QuestionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findByIdAndDeletedAtIsNull(Long id);

    Page<Question> findAllByUserIdAndDeletedAtIsNull(Long userId, Pageable pageable);

    Page<Question> findAllByDeletedAtIsNull(Pageable pageable);

    Page<Question> findAllByStatusAndDeletedAtIsNull(QuestionStatus status, Pageable pageable);
}

package com.seo2yeon.students.domain.qna.repository;

import com.seo2yeon.students.domain.qna.entity.QuestionFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionFileRepository extends JpaRepository<QuestionFile, Long> {
    List<QuestionFile> findByQuestionId(Long questionId);
    Optional<QuestionFile> findByFileId(Long fileId);
}

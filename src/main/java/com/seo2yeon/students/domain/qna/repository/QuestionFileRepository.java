package com.seo2yeon.students.domain.qna.repository;

import com.seo2yeon.students.domain.qna.entity.QuestionFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionFileRepository extends JpaRepository<QuestionFile, Long> {
    List<QuestionFile> findByQuestionId(Long questionId);
}

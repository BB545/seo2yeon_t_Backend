package com.seo2yeon.students.domain.file.repository;

import com.seo2yeon.students.domain.file.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}

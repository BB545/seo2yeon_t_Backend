package com.seo2yeon.students.domain.file.service;

import com.seo2yeon.students.domain.file.dto.FileDownloadResponse;
import com.seo2yeon.students.domain.file.entity.File;
import com.seo2yeon.students.domain.file.repository.FileRepository;
import com.seo2yeon.students.global.exception.CustomException;
import com.seo2yeon.students.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    private static final String FILE_DIR = "uploads/";

    public FileDownloadResponse downloadFile(Long fileId) {

        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new CustomException(ErrorCode.FILE_NOT_FOUND));

        Path path = Paths.get(FILE_DIR + file.getStoredName());

        Resource resource = new FileSystemResource(path);

        if (!resource.exists()) {
            throw new CustomException(ErrorCode.FILE_NOT_FOUND);
        }

        boolean isImage = file.getMimeType().startsWith("image");

        return new FileDownloadResponse(
                resource,
                file.getOriginName(),
                file.getMimeType(),
                isImage
        );
    }
}

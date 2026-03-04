package com.seo2yeon.students.domain.file.controller;

import com.seo2yeon.students.domain.file.dto.FileDownloadResponse;
import com.seo2yeon.students.domain.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;

    @GetMapping("/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {

        FileDownloadResponse response = fileService.downloadFile(fileId);

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.parseMediaType(response.getContentType()));

        if (response.isImage()) {
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline");
        } else {
            headers.set(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + response.getFileName() + "\"");
        }

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(response.getResource());
    }
}

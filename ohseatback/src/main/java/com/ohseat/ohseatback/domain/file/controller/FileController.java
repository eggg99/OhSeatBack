package com.ohseat.ohseatback.domain.file.controller;

import com.ohseat.ohseatback.domain.file.entity.FileEntity;
import com.ohseat.ohseatback.domain.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    // 단건 파일 다운로드 (또는 미리보기)
    @GetMapping("/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer fileId) throws IOException {
        FileEntity file = fileService.getFile(fileId);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }

        PathResource resource = new PathResource(Paths.get(file.getFileUrl()));
        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
        try {
            mediaType = MediaType.parseMediaType(file.getFileType());
        } catch (Exception ignored) {}

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + file.getFileName() + "\"")
                .body(resource);
    }

}

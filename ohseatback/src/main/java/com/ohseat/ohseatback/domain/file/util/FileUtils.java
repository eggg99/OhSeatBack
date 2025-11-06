package com.ohseat.ohseatback.domain.file.util;

import com.ohseat.ohseatback.domain.file.entity.FileEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

// 파일 저장/삭제 로직 전담
@Component
public class FileUtils {

    private final String UPLOAD_DIR = "uploads"; // 서버 저장 디렉토리

    public FileUtils() throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
    }

    public FileEntity storeFile(MultipartFile file, String entityType, Integer entityId) throws IOException {
        String originalName = file.getOriginalFilename();
        String ext = getExtension(originalName);
        String storedName = UUID.randomUUID() + "." + ext;

        Path targetPath = Paths.get(UPLOAD_DIR, storedName);
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        return FileEntity.builder()
                .fileName(originalName)
                .fileUrl(targetPath.toString())
                .fileSize((int)file.getSize())
                .fileType(ext)
                .entityType(entityType)
                .entityId(entityId)
                .build();
    }

    public void deleteFile(String filePath) throws IOException {
        Files.deleteIfExists(Paths.get(filePath));
    }

    private String getExtension(String filename) {
        int idx = filename.lastIndexOf(".");
        return (idx > 0) ? filename.substring(idx + 1) : "";
    }

}

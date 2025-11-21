package com.ohseat.ohseatback.domain.file.service;

import com.ohseat.ohseatback.domain.file.entity.FileEntity;
import com.ohseat.ohseatback.domain.file.mapper.FileMapper;
import com.ohseat.ohseatback.domain.file.util.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

// DB + 파일 연동
@Service
@RequiredArgsConstructor
public class FileService {

    private final FileMapper fileMapper;
    private final FileUtils fileUtils;

    // 기존 메서드 유지(호환)
    public void saveFiles(List<MultipartFile> files, String entityType, Integer entityId) throws IOException {
        saveFiles(files, null, entityType, entityId);
    }

    // 새 오버로드 (representatives: 같은 순서의 "Y"/"N" 또는 "true"/"false")
    public void saveFiles(List<MultipartFile> files, List<String> representatives, String entityType, Integer entityId) throws IOException {
        if (files == null || files.isEmpty()) return;

        // 기존 대표 존재 여부 확인
        boolean existingHasRep = fileMapper.selectFilesByEntity(entityType, entityId)
                .stream()
                .anyMatch(f -> "Y".equalsIgnoreCase(f.getIsRepresentative()));

        for (int i = 0; i < files.size(); i++) {
            MultipartFile mf = files.get(i);
            FileEntity entity = fileUtils.storeFile(mf, entityType, entityId);

            // 프론트에서 대표 여부를 보냈다면(같은 인덱스 사용)
            if (representatives != null && representatives.size() > i) {
                String v = representatives.get(i);
                boolean isY = "Y".equalsIgnoreCase(v) || "true".equalsIgnoreCase(v) || "1".equals(v);
                entity.setIsRepresentative(isY ? "Y" : "N");
            } else {
                // 프론트 미전달 - 기존 규칙 : 대표가 없으면 첫 업로드 파일을 대표로 함
                if (!existingHasRep) {
                    entity.setIsRepresentative("Y");
                    existingHasRep = true;
                } else {
                    entity.setIsRepresentative("N");
                }
            }

            fileMapper.insertFile(entity);
        }
    }

    public List<FileEntity> getFiles(String entityType, Integer entityId) {
        return fileMapper.selectFilesByEntity(entityType, entityId);
    }

    public FileEntity getFile(Integer fileId) {
        return fileMapper.selectFileById(fileId);
    }

    public void deleteFile(Integer fileId) throws IOException {
        FileEntity file = fileMapper.selectFileById(fileId);
        if (file != null) {
            fileUtils.deleteFile(file.getFileUrl());
            fileMapper.deleteFile(fileId);
        }
    }
}

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

    public void saveFiles(List<MultipartFile> files, String entityType, Integer entityId) throws IOException {
        if (files == null || files.isEmpty()) return;

        for (MultipartFile file : files) {
            FileEntity entity = fileUtils.storeFile(file, entityType, entityId);
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

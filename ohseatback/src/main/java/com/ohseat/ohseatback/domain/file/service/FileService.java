package com.ohseat.ohseatback.domain.file.service;

import com.ohseat.ohseatback.domain.file.dto.FileResponse;
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

    // 임시 파일 업로드 (entityId = 0)
    public FileResponse uploadTempFile(MultipartFile file) throws IOException {
        FileEntity entity = fileUtils.storeFile(file, "TEMP", 0);
        fileMapper.insertFile(entity);
        return FileResponse.from(entity);
    }

    // 이미 업로드된 파일(fileId)을 게시글(entity)에 연결
    public void attachFilesToEntity(List<Integer> fileIds, String entityType, Integer entityId) {
        if (fileIds == null) return;
        for (Integer fileId : fileIds) {
            fileMapper.updateEntityId(fileId, entityId, entityType);
        }
    }

    // 대표 이미지 지정
    public void setRepresentativeFile(Integer fileId, String entityType, Integer entityId) {
        List<FileEntity> files = fileMapper.selectFilesByEntity(entityType, entityId);
        for (FileEntity f : files) {
            f.setIsRepresentative(f.getFileId().equals(fileId) ? "Y" : "N");
            fileMapper.updateRepresentative(f);
        }
    }

    public List<FileEntity> getFiles(String entityType, Integer entityId) {
        return fileMapper.selectFilesByEntity(entityType, entityId);
    }

    public FileEntity getFile(Integer fileId) {
        return fileMapper.selectFileById(fileId);
    }

    public void deleteFile(Integer fileId) {
        FileEntity file = fileMapper.selectFileById(fileId);
        if (file != null) {
            try {
                fileUtils.deleteFile(file.getFileUrl());
                fileMapper.deleteFile(fileId);
            } catch (IOException e) {
                throw new RuntimeException("파일 삭제 실패 : " + fileId, e);
            }
        }
    }

    // event 로직 추가
    public void save(MultipartFile file, Integer entityId, String entityType, String role) {
        if (file == null) return;

        try {
            FileEntity entity = fileUtils.storeFile(file, entityType, entityId);
            entity.setFileRole(role);
            entity.setIsRepresentative("N");
            fileMapper.insertFile(entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void save(List<MultipartFile> files, Integer entityId, String entityType, String role) {
        if (files == null) return;
        for (MultipartFile file : files) {
            save(file, entityId, entityType, role);
        }
    }

    public void deleteByEntity(String entityType, Integer entityId) {
        List<FileEntity> files = fileMapper.selectFilesByEntity(entityType, entityId);
        for (FileEntity f : files) {
            deleteFile(f.getFileId());
        }
    }

    public List<FileResponse> getFiles(String entityType, Integer entityId, String role) {
        return fileMapper.selectFilesByEntity(entityType, entityId).stream()
                .map(FileResponse::from)
                .toList();
    }

    public void deleteFiles(List<Integer> ids) {
        if (ids == null) return;
        for (Integer id : ids) {
            deleteFile(id);
        }
    }
}

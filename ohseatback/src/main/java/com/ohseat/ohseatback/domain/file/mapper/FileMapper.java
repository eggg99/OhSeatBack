package com.ohseat.ohseatback.domain.file.mapper;

import com.ohseat.ohseatback.domain.file.entity.FileEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {
    void insertFile(FileEntity file);

    FileEntity selectFileById(Integer fileId);

    List<FileEntity> selectFilesByEntity(String entityType, Integer entityId);

    void deleteFile(Integer fileId);

    void updateRepresentative(FileEntity file);

    // 게시글 수정 시 newFileIds 처리용
    void updateEntityId(Integer fileId, Integer entityId, String entityType);

    void deleteEmptyFiles(String entityType, Integer entityId);
}

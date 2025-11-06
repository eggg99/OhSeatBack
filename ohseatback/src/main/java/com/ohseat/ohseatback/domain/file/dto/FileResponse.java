package com.ohseat.ohseatback.domain.file.dto;

import com.ohseat.ohseatback.domain.file.entity.FileEntity;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileResponse {
    private Integer fileId;
    private String fileName;
    private String fileUrl;
    private Integer fileSize;
    private String fileType;

    public static FileResponse from(FileEntity entity) {
        return FileResponse.builder()
                .fileId(entity.getFileId())
                .fileName(entity.getFileName())
                .fileUrl(entity.getFileUrl())
                .fileSize(entity.getFileSize())
                .fileType(entity.getFileType())
                .build();
    }
}

package com.ohseat.ohseatback.domain.file.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity {
    private Integer fileId;       // 파일 PK
    private String fileName;      // 원본 파일명
    private String fileUrl;       // 저장 경로(혹은 URL)
    private Integer fileSize;     // 파일 크기(byte)
    private String fileType;      // 확장자(MIME or ext)
    private Integer entityId;     // 연결된 엔티티의 PK
    private String entityType;    // PROFILE / CINESQUARE_POST / COMMENT
    private String uploadedAt;    // 업로드 시각
}

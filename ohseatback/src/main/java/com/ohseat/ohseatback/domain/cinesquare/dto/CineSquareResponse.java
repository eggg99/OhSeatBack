package com.ohseat.ohseatback.domain.cinesquare.dto;

import com.ohseat.ohseatback.domain.file.dto.FileResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/** 조회용 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CineSquareResponse {
    private Integer postId;
    private Integer categoryId;
    private String categoryName;
    private String title;
    private String content;
    private Integer views;
    private LocalDateTime createdAt;
    private Integer authorId;
    private String authorNickname;
    private String city;
    private String district;

    // 파일 관련
    private List<FileResponse> files;           // 단건 조회 시 전체 파일 리스트
    private FileResponse representativeFile;    // 목록 조회 시 대표 이미지 1개
    private Integer totalFiles;                 // 목록 조회 시 파일 총 개수
}

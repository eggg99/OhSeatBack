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
    private List<FileResponse> files;   // 첨부파일 리스트 추가
}

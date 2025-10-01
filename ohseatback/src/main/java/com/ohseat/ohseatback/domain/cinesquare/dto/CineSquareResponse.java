package com.ohseat.ohseatback.domain.cinesquare.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
}

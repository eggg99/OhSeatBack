package com.ohseat.ohseatback.dto;

import lombok.Data;

import java.time.LocalDateTime;

/** 조회용 */
@Data
public class CineSquareResponse {
    private Integer postId;
    private String category;
    private String title;
    private String content;
    private Integer views;
    private LocalDateTime createdAt;
    private Integer authorId;
}

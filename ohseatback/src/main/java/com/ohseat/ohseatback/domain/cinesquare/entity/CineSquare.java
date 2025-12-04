package com.ohseat.ohseatback.domain.cinesquare.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CineSquare {
    private Integer postId;
    private Integer categoryId;
    private String categoryName;    // notice, free, event ...
    private String title;
    private String content;
    private Integer views;
    private LocalDateTime createdAt;
    private Integer authorId;
    // 위치
    private String city;
    private String district;

    private Boolean isNotice;
}

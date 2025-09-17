package com.ohseat.ohseatback.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CineSquare {
    private Integer postId;
    private String category;    // notice, free, event ...
    private String title;
    private String content;
    private Integer views;
    private LocalDateTime createdAt;
    private Integer authorId;
}

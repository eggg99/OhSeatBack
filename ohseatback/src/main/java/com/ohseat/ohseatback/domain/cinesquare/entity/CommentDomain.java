package com.ohseat.ohseatback.domain.cinesquare.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDomain {
    private Integer commentId;
    private Integer postId;
    private Integer commenterId;
    private String content;
    private LocalDateTime createdAt;
}

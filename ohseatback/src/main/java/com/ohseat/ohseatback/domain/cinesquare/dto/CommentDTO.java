package com.ohseat.ohseatback.domain.cinesquare.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Integer commentId;
    private Integer postId;
    private Integer commenterId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String authorNickname;
}

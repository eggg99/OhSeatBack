package com.ohseat.ohseatback.domain.cinesquare.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentDomain {
    private Integer commentId;
    private Integer postId;
    private Integer commenterId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

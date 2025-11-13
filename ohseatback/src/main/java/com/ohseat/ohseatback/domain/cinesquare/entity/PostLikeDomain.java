package com.ohseat.ohseatback.domain.cinesquare.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostLikeDomain {
    private Integer postLikeId;
    private Integer postId;
    private Integer postLikeUserId;
    private LocalDateTime createdAt;
}

package com.ohseat.ohseatback.domain.cinesquare.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PostLikeDomain {
    private Integer postLikeId;
    private Integer postId;
    private Integer postLikeUserId;
    private LocalDateTime createdAt;
}

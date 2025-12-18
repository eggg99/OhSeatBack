package com.ohseat.ohseatback.domain.recommend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class CommentDomain {
    private Integer commentId;
    private Integer postId;
    private Integer commenterId;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}

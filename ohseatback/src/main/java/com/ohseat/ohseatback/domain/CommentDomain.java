package com.ohseat.ohseatback.domain;

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
}

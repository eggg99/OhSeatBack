package com.ohseat.ohseatback.dto.recommend;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class CommentDTO {
    private Integer commentId;
    private String content;
    private String authorNickname;
    private Timestamp createdAt;
}

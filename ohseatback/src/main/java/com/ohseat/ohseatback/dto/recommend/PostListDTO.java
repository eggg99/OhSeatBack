package com.ohseat.ohseatback.dto.recommend;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
public class PostListDTO {
    private Integer postId;
    private String title;
    private String content;
    private String authorNickname;
    private Integer views;
    private Timestamp createdAt;
    private Long commentCount;
}

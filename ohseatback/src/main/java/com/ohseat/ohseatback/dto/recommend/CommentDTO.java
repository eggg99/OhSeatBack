package com.ohseat.ohseatback.dto.recommend;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class CommentDTO {
    private Integer commentId;
    private Integer postId;
    private Integer commenterId;
    private String content;
    private String authorNickname;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년MM월dd일 HH시mm분", timezone = "Asia/Seoul")
    private Timestamp createdAt;
}

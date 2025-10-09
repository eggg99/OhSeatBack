package com.ohseat.ohseatback.domain.recommend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class PostDTO {
    private Integer postId;
    private String title;
    private String content;
    private String authorNickname;
    private Integer views;
    private Integer likeCount;
    private boolean liked;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년MM월dd일 HH시mm분", timezone = "Asia/Seoul")
    private Timestamp createdAt;
    private Long commentCount;
    private Integer multiplexId;
    private Integer areaId;
    private String cinemaId;
    private String screenId;
    private String authorId;
}

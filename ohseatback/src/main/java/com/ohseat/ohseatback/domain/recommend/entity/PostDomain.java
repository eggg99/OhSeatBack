package com.ohseat.ohseatback.domain.recommend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class PostDomain {
    // Getters & Setters
    private Integer postId;
    private Integer authorId;
    private String title;
    private String content;
    private Integer views;
    private Integer likeCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년MM월dd일 HH시mm분", timezone = "Asia/Seoul")
    private Timestamp createdAt;

    // FK
    private Integer multiplexId;
    private Integer areaId;
    private String cinemaId;
    private String screenId;
}

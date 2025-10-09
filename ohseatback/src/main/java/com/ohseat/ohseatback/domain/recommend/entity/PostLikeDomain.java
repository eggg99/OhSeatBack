package com.ohseat.ohseatback.domain.recommend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class PostLikeDomain {
    private Integer postLikeId;
    private Integer postId;
    private Integer postLikeUserId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년MM월dd일 HH시mm분", timezone = "Asia/Seoul")
    private Timestamp createdAt;
}

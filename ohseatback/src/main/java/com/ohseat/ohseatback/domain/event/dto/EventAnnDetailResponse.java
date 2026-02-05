package com.ohseat.ohseatback.domain.event.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventAnnDetailResponse {
    private Integer eventId;
    private Integer categoryId;
    private Integer authorId;
    private String title;
    private String content;
    private Integer views;
    private LocalDateTime createdAt;

    private Integer likeCount;  // 좋아요 개수
    private boolean liked;      // 좋아요 여부
    private Integer prevSeq;    // 이전글
    private Integer nextSeq;    // 다음글

}

package com.ohseat.ohseatback.domain.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class NoticeListResponse {

    private Long noticeId;
    private String title;
    private Long authorId;
    private String authorName;
    private LocalDateTime createdAt;
    private int views;
    private int isPinned;
}

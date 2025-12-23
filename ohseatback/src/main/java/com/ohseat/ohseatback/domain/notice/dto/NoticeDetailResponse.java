package com.ohseat.ohseatback.domain.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class NoticeDetailResponse {

    private Long noticeId;
    private String targetBoard;
    private String title;
    private String content;
    private Long authorId;
    private String authorNickName;
    private LocalDateTime createdAt;
    private int views;
}

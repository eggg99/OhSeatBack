package com.ohseat.ohseatback.domain.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class NoticeAdminListResponse {

    private Long noticeId;
    private String targetBoard;
    private String title;
    private Long authorId;
    private String authorNickname;
    private LocalDateTime createdAt;
    private int views;
    private int isPinned;
    private int isActive;
}

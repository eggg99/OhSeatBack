package com.ohseat.ohseatback.domain.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class NoticeDetailResponse {

    private Integer noticeId;
    private String targetBoard;
    private String title;
    private String content;
    private Integer authorId;
    private String authorNickName;
    private LocalDateTime createdAt;
    private int views;
}

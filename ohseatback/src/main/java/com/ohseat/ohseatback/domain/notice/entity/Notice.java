package com.ohseat.ohseatback.domain.notice.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Notice {
    private Integer noticeId;
    private String targetBoard;     // CINESQUARE / RECOMMEND
    private Integer authorId;

    private String title;
    private String content;

    private Integer views;
    private Boolean isPinned;
    private Boolean isActive;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

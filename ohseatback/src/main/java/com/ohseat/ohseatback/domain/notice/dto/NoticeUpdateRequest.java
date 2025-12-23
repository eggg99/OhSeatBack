package com.ohseat.ohseatback.domain.notice.dto;

import lombok.*;

@Getter
@NoArgsConstructor
public class NoticeUpdateRequest {

    private String title;
    private String content;
}
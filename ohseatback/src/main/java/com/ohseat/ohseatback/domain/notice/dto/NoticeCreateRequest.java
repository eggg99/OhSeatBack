package com.ohseat.ohseatback.domain.notice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeCreateRequest {

    private String targetBoard;     // CINESQUARE, RECOMMEND
    private String title;
    private String content;
}

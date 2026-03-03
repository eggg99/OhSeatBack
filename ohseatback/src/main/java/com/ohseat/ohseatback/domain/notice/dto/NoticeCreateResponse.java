package com.ohseat.ohseatback.domain.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoticeCreateResponse {

    private Integer noticeId;
    private String message;
}

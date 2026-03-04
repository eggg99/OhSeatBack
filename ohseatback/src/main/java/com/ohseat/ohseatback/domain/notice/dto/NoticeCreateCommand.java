package com.ohseat.ohseatback.domain.notice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeCreateCommand {

    private Integer noticeId;
    private String targetBoard;
    private Integer authorId;
    private String title;
    private String content;
}

package com.ohseat.ohseatback.domain.notice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeActiveRequest {

    @JsonProperty("isActive")
    private boolean isActive;
}

package com.ohseat.ohseatback.dto;

import lombok.Data;

/** 작성, 수정용 */
@Data
public class CineSquareRequest {
    private String category;
    private String title;
    private String content;
}

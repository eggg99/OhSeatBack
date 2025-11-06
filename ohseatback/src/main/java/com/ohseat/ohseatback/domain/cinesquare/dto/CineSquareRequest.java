package com.ohseat.ohseatback.domain.cinesquare.dto;

import lombok.*;

/** 작성, 수정용 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CineSquareRequest {
    private Integer categoryId;
    private String title;
    private String content;
    private String city;
    private String district;
}

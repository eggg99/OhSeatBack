package com.ohseat.ohseatback.dto.recommend;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ScreenDTO {
    private Integer multiplexId;
    private String cinemaId;
    private String screenId;
    private String screenName;
}

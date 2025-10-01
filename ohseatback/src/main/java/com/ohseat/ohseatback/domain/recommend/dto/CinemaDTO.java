package com.ohseat.ohseatback.domain.recommend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CinemaDTO {
    private Integer multiplexId;
    private Integer areaId;
    private String cinemaId;
    private String cinemaName;
    private String cinemaAddr;
}

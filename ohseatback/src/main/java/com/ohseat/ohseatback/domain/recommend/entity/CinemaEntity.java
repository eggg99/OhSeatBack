package com.ohseat.ohseatback.domain.recommend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CinemaEntity {
    // Getters & Setters
    private Integer multiplexId;
    private Integer areaId;
    private String cinemaId;
    private String cinemaName;
    private String cinemaAddr;
}

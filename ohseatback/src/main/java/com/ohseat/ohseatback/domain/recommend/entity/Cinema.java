package com.ohseat.ohseatback.domain.recommend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Cinema {
    // Getters & Setters
    private Integer multiplexId;
    private String multiplexName;
    private Integer areaId;
    private String areaName;
    private String cinemaId;
    private String cinemaName;
    private String cinemaAddr;
    private String screenId;
    private String screenName;
}

package com.ohseat.ohseatback.domain.event.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EventCreateRequestDTO {

    private Integer categoryId;
    private String title;

    private LocalDate startDt;
    private LocalDate endDt;

    private Integer annCount;
}

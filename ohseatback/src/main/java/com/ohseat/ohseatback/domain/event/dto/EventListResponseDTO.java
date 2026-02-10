package com.ohseat.ohseatback.domain.event.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EventListResponseDTO {

    private Integer eventId;
    private Integer categoryId;
    private String title;

    private LocalDate startDt;
    private LocalDate endDt;

    private boolean isEnd;

    private String imgUrl;  //  THUMB
}

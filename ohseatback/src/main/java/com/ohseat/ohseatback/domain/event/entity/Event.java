package com.ohseat.ohseatback.domain.event.entity;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Event {
    private Integer eventId;
    private String eventSe;     // EVT / ANN
    private Integer categoryId;
    private Integer authorId;

    private String title;
    private String content;

    private Integer annCount;

    private LocalDate startDt;
    private LocalDate endDt;

    private Integer views;
    private Integer likeCount;

    private LocalDateTime createdAt;
}

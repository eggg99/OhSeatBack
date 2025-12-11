package com.ohseat.ohseatback.domain.event.dto;

import lombok.Data;

@Data
public class EventAnnRequest {
    private Integer eventId;
    private Integer categoryId;
    private Integer authorId;
    private String title;
    private String content;
}

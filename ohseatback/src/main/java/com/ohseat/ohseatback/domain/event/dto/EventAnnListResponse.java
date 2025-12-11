package com.ohseat.ohseatback.domain.event.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventAnnListResponse {
    private Integer eventId;
    private Integer categoryId;
    private String title;
    private String content;
    private Integer views;
    private LocalDateTime createdAt;
}

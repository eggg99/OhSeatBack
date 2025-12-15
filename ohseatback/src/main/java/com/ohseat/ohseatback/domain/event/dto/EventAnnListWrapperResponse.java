package com.ohseat.ohseatback.domain.event.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class EventAnnListWrapperResponse {
    private int totalCount;
    private List<EventAnnListResponse> list;
}

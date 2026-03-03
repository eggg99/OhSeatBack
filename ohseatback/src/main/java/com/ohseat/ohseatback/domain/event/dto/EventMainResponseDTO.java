package com.ohseat.ohseatback.domain.event.dto;

import com.ohseat.ohseatback.domain.file.dto.FileResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class EventMainResponseDTO {

    private Integer eventId;
    private Integer categoryId;
    private String title;

    private LocalDate startDt;
    private LocalDate endDt;
    private boolean isEnd;

    private Integer annCount;

    private List<FileResponse> files;
}

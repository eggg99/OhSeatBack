package com.ohseat.ohseatback.domain.event.dto;

import com.ohseat.ohseatback.domain.file.dto.FileResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class EventDetailResponseDTO {

    private Integer eventId;
    private Integer categoryId;
    private Integer authorId;
    private String title;

    private Integer annCount;

    private LocalDate startDt;
    private LocalDate endDt;
    private boolean isEnd;

    private Integer views;
    private Integer likeCount;
    private boolean liked;

    // CONTENT IMAGE
    private List<FileResponse> files;

    private Integer prevSeq;
    private Integer nextSeq;
}

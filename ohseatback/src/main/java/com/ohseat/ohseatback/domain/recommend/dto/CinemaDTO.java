package com.ohseat.ohseatback.domain.recommend.dto;

import com.ohseat.ohseatback.domain.recommend.entity.CinemaEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CinemaDTO {
    private Integer multiplexId;
    private Integer areaId;
    private String cinemaId;
    private String cinemaName;
    private String cinemaAddr;

    // 변환용 생성자
    public CinemaDTO(CinemaEntity entity) {
        this.multiplexId = entity.getMultiplexId();
        this.areaId = entity.getAreaId();
        this.cinemaId = entity.getCinemaId();
        this.cinemaName = entity.getCinemaName();
        this.cinemaAddr = entity.getCinemaAddr();
    }

    // 정적 팩토리 메서드 (가독성용)
    public static CinemaDTO of(CinemaEntity entity) {
        return new CinemaDTO(entity);
    }
}

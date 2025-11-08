package com.ohseat.ohseatback.domain.boxoffice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 조회용 DTO */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoxofficeResponse {
    private String rank;       // 순위
    private String movieNm;    // 영화명
    private String openDt;     // 개봉일
    private String audiAcc;    // 누적 관객수
    private String posterUrl;  // KMDb 포스터 URL
}

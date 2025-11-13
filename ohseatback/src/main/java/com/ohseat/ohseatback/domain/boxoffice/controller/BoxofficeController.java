package com.ohseat.ohseatback.domain.boxoffice.controller;

import com.ohseat.ohseatback.domain.boxoffice.dto.BoxofficeResponse;
import com.ohseat.ohseatback.domain.boxoffice.service.BoxofficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/boxoffice")
@RequiredArgsConstructor
public class BoxofficeController {

    private final BoxofficeService boxofficeService;

    /**
     * 이번 주 기준 박스오피스 + KMDb 포스터 포함 조회
     */
    @GetMapping("/list")
    public ResponseEntity<List<BoxofficeResponse>> getAllBoxoffice() {
        List<BoxofficeResponse> result = boxofficeService.getBoxofficeWithPoster();
        return ResponseEntity.ok(result);
    }
}

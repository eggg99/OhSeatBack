package com.ohseat.ohseatback.domain.event.controller;

import com.ohseat.ohseatback.domain.event.dto.*;
import com.ohseat.ohseatback.domain.event.service.EventAnnService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/event/announcement")
public class EventAnnController {

    private final EventAnnService eventAnnService;

    // 이벤트 당첨발표 전체 게시글 조회
    @GetMapping("/list")
    public ResponseEntity<Page<EventAnnListResponse>> list(
            @RequestParam(defaultValue = "0") Integer categoryId,
            @RequestParam(defaultValue = "0") Integer searchType,
            @RequestParam(required = false) String searchValue,
            @RequestParam(defaultValue = "latest") String orderType,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(eventAnnService.list(categoryId, searchType, searchValue, orderType, page, size));
    }

    // 이벤트 당첨발표 게시글 상세 조회
    @GetMapping("/{eventId}")
    public ResponseEntity<EventAnnDetailResponse> detail(@PathVariable Integer eventId) {
        return ResponseEntity.ok(eventAnnService.detail(eventId));
    }

    // 좋아요
    @PostMapping("/{eventId}/like")
    public ResponseEntity<Void> like(@PathVariable Integer eventId) {
        eventAnnService.like(eventId);
        return ResponseEntity.ok().build();
    }

    // 좋아요 취소
    @DeleteMapping("/{eventId}/like")
    public ResponseEntity<Void> unlike(@PathVariable Integer eventId) {
        eventAnnService.unlike(eventId);
        return ResponseEntity.ok().build();
    }

}

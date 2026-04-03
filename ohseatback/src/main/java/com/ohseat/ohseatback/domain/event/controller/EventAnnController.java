package com.ohseat.ohseatback.domain.event.controller;

import com.ohseat.ohseatback.domain.event.dto.*;
import com.ohseat.ohseatback.domain.event.service.EventAnnService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(eventAnnService.list(categoryId, searchType, searchValue, orderType, page, size));
    }

    // 이벤트 당첨발표 게시글 상세 조회
    @GetMapping("/{eventId}")
    public ResponseEntity<EventAnnDetailResponse> detail(@PathVariable Integer eventId, HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(eventAnnService.detail(eventId, request, response));
    }

    // 좋아요
    @PostMapping("/{eventId}/like")
    public ResponseEntity<String> like(@PathVariable Integer eventId) {
        eventAnnService.like(eventId);
        return ResponseEntity.ok("좋아요 성공");
    }

    // 좋아요 취소
    @DeleteMapping("/{eventId}/like")
    public ResponseEntity<String> unlike(@PathVariable Integer eventId) {
        eventAnnService.unlike(eventId);
        return ResponseEntity.ok("좋아요 취소");
    }

}

package com.ohseat.ohseatback.domain.event.controller;

import com.ohseat.ohseatback.domain.event.dto.*;
import com.ohseat.ohseatback.domain.event.service.EventAnnService;
import com.ohseat.ohseatback.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Delete;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/event/announcement")
public class EventAnnController {

    private final EventAnnService eventAnnService;

    // 이벤트 당첨발표 게시글 등록
    @PostMapping
    public ResponseEntity<EventAnnWriteResponse> create(@RequestBody EventAnnRequest request) {
        request.setAuthorId(SecurityUtil.getCurrentUserId());
        return ResponseEntity.ok(eventAnnService.create(request));
    }

    // 이벤트 당첨발표 전체 게시글 조회 >> request 수정 필요
    @GetMapping("/list")
    public ResponseEntity<EventAnnListWrapperResponse> list(
            @RequestParam(defaultValue = "0") Integer categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(eventAnnService.list(categoryId, page, size));
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

    // 이벤트 당첨발표 게시글 수정 >> msg 성공, 실패 여부 보내야함
    @PutMapping("/{eventId}")
    public ResponseEntity<Void> update(@PathVariable Integer eventId, @RequestBody EventAnnRequest request) {
        eventAnnService.update(eventId, request);
        return ResponseEntity.ok().build();
    }

    // 이벤트 당첨발표 게시글 삭제 >> msg 성공, 실패 여부
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> delete(@PathVariable Integer eventId) {
        eventAnnService.delete(eventId);
        return ResponseEntity.ok().build();
    }

}

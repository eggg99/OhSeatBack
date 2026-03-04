package com.ohseat.ohseatback.domain.event.controller;

import com.ohseat.ohseatback.domain.event.dto.EventAnnRequest;
import com.ohseat.ohseatback.domain.event.dto.EventAnnWriteResponse;
import com.ohseat.ohseatback.domain.event.service.EventAnnService;
import com.ohseat.ohseatback.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/event")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminEventController {

    private final EventAnnService eventAnnService;

    // 이벤트 당첨발표 게시글 등록
    @PostMapping("/announcement")
    public ResponseEntity<EventAnnWriteResponse> create(@RequestBody EventAnnRequest request) {
        request.setAuthorId(SecurityUtil.getCurrentUserId());
        return ResponseEntity.ok(eventAnnService.create(request));
    }

    // 이벤트 당첨발표 게시글 수정
    @PutMapping("/announcement/{eventId}")
    public ResponseEntity<String> update(@PathVariable Integer eventId, @RequestBody EventAnnRequest request) {
        eventAnnService.update(eventId, request);
        return ResponseEntity.ok("게시글 수정 완료");
    }

    // 이벤트 당첨발표 게시글 삭제
    @DeleteMapping("/announcement/{eventId}")
    public ResponseEntity<String> delete(@PathVariable Integer eventId) {
        eventAnnService.delete(eventId);
        return ResponseEntity.ok("게시글 삭제 완료");
    }
}

package com.ohseat.ohseatback.domain.notice.controller;

import com.ohseat.ohseatback.domain.notice.dto.NoticeActiveRequest;
import com.ohseat.ohseatback.domain.notice.dto.NoticeAdminListResponse;
import com.ohseat.ohseatback.domain.notice.dto.NoticeCreateRequest;
import com.ohseat.ohseatback.domain.notice.dto.NoticeUpdateRequest;
import com.ohseat.ohseatback.domain.notice.service.AdminNoticeService;
import com.ohseat.ohseatback.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/notices")
@PreAuthorize("hasRole('ADMIN')")
public class AdminNoticeController {

    private final AdminNoticeService adminNoticeService;

    // 관리자 공지사항 전체 조회
    @GetMapping
    public List<NoticeAdminListResponse> list(@RequestParam String targetBoard) {
        return adminNoticeService.getAdminNoticeList(targetBoard);
    }

    // 공지사항 작성
    @PostMapping
    public void create(@RequestBody NoticeCreateRequest request) {
        Integer adminId = SecurityUtil.getCurrentUserId();
        adminNoticeService.createNotice(request, adminId);
    }
    
    // 공지사항 수정
    @PutMapping("/{noticeId}")
    public ResponseEntity<Void> update(@PathVariable Integer noticeId, @RequestBody NoticeUpdateRequest request) {
        adminNoticeService.updateNotice(noticeId, request);
        return ResponseEntity.ok().build();
    }

    // 공지사항 삭제 (비노출)
    @DeleteMapping("/{noticeId}")
    public void deactive(@PathVariable Integer noticeId) {
        adminNoticeService.deactiveNotice(noticeId);
    }

    // 고정 활성 / 비활성 업데이트
    @PatchMapping("/{noticeId}/active")
    public ResponseEntity<Void> updateActive(@PathVariable Integer noticeId, @RequestBody NoticeActiveRequest request) {
        System.out.println("isActive = " + request.isActive());
        adminNoticeService.updateActiveStatus(noticeId, request.isActive());
        return ResponseEntity.ok().build();
    }

    // 고정 / 해제 토글
    @PatchMapping("/{noticeId}/pin")
    public void togglePinned(@PathVariable Integer noticeId) {
        adminNoticeService.togglePinned(noticeId);
    }

}

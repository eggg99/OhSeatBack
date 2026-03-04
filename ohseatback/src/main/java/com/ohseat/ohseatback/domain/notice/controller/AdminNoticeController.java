package com.ohseat.ohseatback.domain.notice.controller;

import com.ohseat.ohseatback.domain.notice.dto.*;
import com.ohseat.ohseatback.domain.notice.service.AdminNoticeService;
import com.ohseat.ohseatback.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<Page<NoticeAdminListResponse>> list(@RequestParam String targetBoard,
                                                              @RequestParam(defaultValue = "1") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(adminNoticeService.getAdminNoticeList(targetBoard, page, size));
    }

    // 관리자 공지사항 상세 조회 (비활성화 포함)
    @GetMapping("/{noticeId}")
    public NoticeDetailResponse detail(@PathVariable Integer noticeId) {
        return adminNoticeService.getNoticeDetailForAdmin(noticeId);
    }

    // 공지사항 작성
    @PostMapping
    public ResponseEntity<NoticeCreateResponse> create(@RequestBody NoticeCreateRequest request) {
        Integer adminId = SecurityUtil.getCurrentUserId();
        Integer noticeId = adminNoticeService.createNotice(request, adminId);

        return ResponseEntity.ok(new NoticeCreateResponse(noticeId, "공지사항 등록 완료"));
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

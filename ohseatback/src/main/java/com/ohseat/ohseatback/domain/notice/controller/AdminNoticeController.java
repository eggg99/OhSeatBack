package com.ohseat.ohseatback.domain.notice.controller;

import com.ohseat.ohseatback.domain.notice.dto.NoticeCreateRequest;
import com.ohseat.ohseatback.domain.notice.service.AdminNoticeService;
import com.ohseat.ohseatback.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/notices")
@PreAuthorize("hasRole('ADMIN')")
public class AdminNoticeController {

    private final AdminNoticeService adminNoticeService;

    // 공지사항 작성
    @PostMapping
    public void create(@RequestBody NoticeCreateRequest request) {
        Integer adminId = SecurityUtil.getCurrentUserId();
        adminNoticeService.createNotice(request, adminId);
    }

    // 공지사항 삭제 (비노출)
    @DeleteMapping("/{noticeId}")
    public void deactive(@PathVariable Long noticeId) {
        adminNoticeService.deactiveNotice(noticeId);
    }

}

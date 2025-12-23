package com.ohseat.ohseatback.domain.notice.controller;

import com.ohseat.ohseatback.domain.notice.dto.NoticeDetailResponse;
import com.ohseat.ohseatback.domain.notice.dto.NoticeListResponse;
import com.ohseat.ohseatback.domain.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notices")
public class NoticeController {

    private final NoticeService noticeService;

    // 공지사항 리스트 조회 (최대 3개)
    @GetMapping
    public List<NoticeListResponse> list (@RequestParam String targetBoard) {
        return noticeService.getActiveNotices(targetBoard);
    }

    // 공지사항 상세 조회
    @GetMapping("/{noticeId}")
    public NoticeDetailResponse detail(@PathVariable Long noticeId) {
        return noticeService.getNoticeDetail(noticeId);
    }

}

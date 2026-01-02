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

    // recommend 공지사항 전체 조회
    @GetMapping("/top")
    public List<NoticeListResponse> topNotices(@RequestParam String targetBoard) {
        return noticeService.getTopPinnedNotices(targetBoard);
    }

    // 공지사항 상세 조회
    @GetMapping("/{noticeId}")
    public NoticeDetailResponse detail(@PathVariable Long noticeId) {
        return noticeService.getNoticeDetail(noticeId);
    }

}

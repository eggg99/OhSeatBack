package com.ohseat.ohseatback.domain.notice.service;

import com.ohseat.ohseatback.domain.notice.dto.NoticeDetailResponse;
import com.ohseat.ohseatback.domain.notice.dto.NoticeListResponse;
import com.ohseat.ohseatback.domain.notice.mapper.NoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeMapper noticeMapper;

    // recommend 공지사항 전체 조회
    @Transactional(readOnly = true)
    public List<NoticeListResponse> getTopPinnedNotices(String targetBoard) {
        return noticeMapper.selectTopPinnedNotices(targetBoard);
    }

    // 공지 상세 조회
    @Transactional(readOnly = true)
    public NoticeDetailResponse getNoticeDetail(Long noticeId) {
        noticeMapper.increaseViews(noticeId);
        return noticeMapper.selectNoticeDetail(noticeId);
    }
}

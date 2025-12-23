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

    @Transactional(readOnly = true)
    public List<NoticeListResponse> getActiveNotices(String targetBoard) {
        return noticeMapper.selectActiveNotices(targetBoard);
    }

    @Transactional
    public NoticeDetailResponse getNoticeDetail(Long noticeId) {
        noticeMapper.increaseViews(noticeId);
        return noticeMapper.selectNoticeDetail(noticeId);
    }
}

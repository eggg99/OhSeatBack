package com.ohseat.ohseatback.domain.notice.service;

import com.ohseat.ohseatback.domain.common.service.ViewCountService;
import com.ohseat.ohseatback.domain.notice.dto.NoticeDetailResponse;
import com.ohseat.ohseatback.domain.notice.dto.NoticeListResponse;
import com.ohseat.ohseatback.domain.notice.mapper.NoticeMapper;
import com.ohseat.ohseatback.security.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeMapper noticeMapper;
    private final ViewCountService viewCountService;

    // recommend 공지사항 전체 조회
    @Transactional(readOnly = true)
    public List<NoticeListResponse> getTopPinnedNotices(String targetBoard) {
        return noticeMapper.selectTopPinnedNotices(targetBoard);
    }

    // 공지 상세 조회
    @Transactional(readOnly = true)
    public NoticeDetailResponse getNoticeDetail(Integer noticeId, HttpServletRequest request, HttpServletResponse response) {
        Integer userId = SecurityUtil.getCurrentUserId();
        NoticeDetailResponse notice = noticeMapper.selectNoticeDetail(noticeId);

        if (notice == null) {
            throw new IllegalStateException("존재하지 않거나 비활성화된 공지입니다.");
        }

        if (viewCountService.canIncrease("notice", noticeId, userId, notice.getAuthorId(), request, response)) {
            noticeMapper.increaseViews(noticeId);

            // 재조회
            notice = noticeMapper.selectNoticeDetail(noticeId);
        }
        return notice;
    }
}

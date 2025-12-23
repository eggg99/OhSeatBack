package com.ohseat.ohseatback.domain.notice.service;

import com.ohseat.ohseatback.domain.notice.dto.NoticeCreateRequest;
import com.ohseat.ohseatback.domain.notice.dto.NoticeUpdateRequest;
import com.ohseat.ohseatback.domain.notice.mapper.NoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminNoticeService {

    private final NoticeMapper noticeMapper;
    private static final int MAX_PINNED = 3;

    // 공지사항 작성
    @Transactional
    public void createNotice(NoticeCreateRequest request, Integer adminId) {
        noticeMapper.insertNotice(request.getTargetBoard(), adminId, request.getTitle(), request.getContent());
    }

    // 공지사항 수정
    @Transactional
    public void updateNotice(Long noticeId, NoticeUpdateRequest request) {
        int updated = noticeMapper.updateNotice(noticeId, request);
        if(updated == 0) {
            throw new IllegalStateException("수정 권한이 없거나 게시글이 존재하지 않습니다.");
        }
    }

    // 공지사항 삭제 (비노출)
    @Transactional
    public void deactiveNotice(Long noticeId) {
        noticeMapper.deactiveNotice(noticeId);
    }

    // 고정 활성 / 비활성 업데이트
    @Transactional
    public void updateActiveStatus(Long noticeId, boolean isActive) {
        int updated = noticeMapper.updateActiveStatus(noticeId, isActive);

        if (updated == 0) {
            throw new IllegalStateException("존재하지 않는 공지입니다.");
        }
    }

    // 고정 / 해제 토글
    @Transactional
    public void togglePinned(Long noticeId) {

        Integer currentPinned = noticeMapper.selectPinnedStatus(noticeId);
        if (currentPinned == null) {
            throw new IllegalStateException("비활성 공지는 고정할 수 없습니다.");
        }

        // 1. 현재 고정 -> 해제
        if (currentPinned == 1) {
            int updated = noticeMapper.updatePinnedStatus(noticeId, 0);
            if (updated == 0) {
                throw new IllegalStateException("고정 해제에 실패했습니다.");
            }
            return;
        }

        // 2. 고정하려는 경우
        String targetBoard = noticeMapper.selectTargetBoardByNoticeId(noticeId);

        int pinnedCount = noticeMapper.countPinnedNotices(targetBoard);
        if (pinnedCount >= MAX_PINNED) {
            throw new IllegalStateException("고정 공지는 최대 " + MAX_PINNED + "개까지 가능합니다.");
        }

        int updated = noticeMapper.updatePinnedStatus(noticeId, 1);
        if (updated == 0) {
            throw new IllegalStateException("고정 처리에 실패했습니다.");
        }
    }

}

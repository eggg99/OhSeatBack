package com.ohseat.ohseatback.domain.notice.service;

import com.ohseat.ohseatback.domain.notice.dto.*;
import com.ohseat.ohseatback.domain.notice.mapper.NoticeMapper;
import com.ohseat.ohseatback.utils.CustomPageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminNoticeService {

    private final NoticeMapper noticeMapper;
    private static final int MAX_PINNED = 1;

    // 관리자 공지사항 전체 조회
    @Transactional(readOnly = true)
    public Page<NoticeAdminListResponse> getAdminNoticeList(String targetBoard, int page, int size) {

        Pageable pageable = CustomPageUtils.getPageable(page, size);

        long totalCount = noticeMapper.selectAdminNoticeCount(targetBoard);

        int offset =(int) pageable.getOffset();
        int limit = pageable.getPageSize();

        List<NoticeAdminListResponse> list = noticeMapper.selectAdminNoticeList(targetBoard, offset, limit);

        return new PageImpl<>(list, pageable, totalCount);
    }

    // 관리자 공지사항 상세 조회 (비활성화 포함)
    @Transactional(readOnly = true)
    public NoticeDetailResponse getNoticeDetailForAdmin(Integer noticeId) {

        NoticeDetailResponse notice = noticeMapper.selectNoticeDetailForAdmin(noticeId);

        if (notice == null) {
            throw new IllegalStateException("존재하지 않는 공지입니다.");
        }

        return notice;
    }

    // 공지사항 작성
    @Transactional
    public Integer createNotice(NoticeCreateRequest request, Integer adminId) {

        NoticeCreateCommand command = new NoticeCreateCommand();
        command.setTargetBoard(request.getTargetBoard());
        command.setAuthorId(adminId);
        command.setTitle(request.getTitle());
        command.setContent(request.getContent());

        noticeMapper.insertNotice(command);

        return command.getNoticeId();
    }

    // 공지사항 수정
    @Transactional
    public void updateNotice(Integer noticeId, NoticeUpdateRequest request) {
        int updated = noticeMapper.updateNotice(noticeId, request);
        if(updated == 0) {
            throw new IllegalStateException("수정 권한이 없거나 게시글이 존재하지 않습니다.");
        }
    }

    // 공지사항 삭제 (비노출)
    @Transactional
    public void deactiveNotice(Integer noticeId) {
        noticeMapper.deactiveNotice(noticeId);
    }

    // 고정 활성 / 비활성 업데이트
    @Transactional
    public boolean updateActiveStatus(Integer noticeId, boolean isActive) {
        int updated = noticeMapper.updateActiveStatus(noticeId, isActive);

        if (updated == 0) {
            throw new IllegalStateException("존재하지 않는 공지입니다.");
        }

        return isActive;    // 변경된 상태 반환
    }

    // 고정 / 해제 토글
    @Transactional
    public boolean togglePinned(Integer noticeId) {

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
            return false;   // 해제됨
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

        return true;    //  고정됨
    }

}

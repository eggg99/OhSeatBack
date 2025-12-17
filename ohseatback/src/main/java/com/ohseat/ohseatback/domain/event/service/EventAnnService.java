package com.ohseat.ohseatback.domain.event.service;

import com.ohseat.ohseatback.domain.event.dto.*;
import com.ohseat.ohseatback.domain.event.mapper.EventAnnMapper;
import com.ohseat.ohseatback.security.SecurityUtil;
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
public class EventAnnService {

    private final EventAnnMapper eventAnnMapper;

    // 이벤트 당첨발표 게시글 등록
    public EventAnnWriteResponse create(EventAnnRequest request) {
        eventAnnMapper.insertAnnEvent(request);
        return new EventAnnWriteResponse(request.getEventId());
    }

    // 이벤트 당첨발표 전체 게시글 조회
    public Page<EventAnnListResponse> list(Integer categoryId, String searchValue, String orderType, int page, int size) {
        // 1. Pageable 생성
        Pageable pageable = CustomPageUtils.getPageable(page, size);

        // 2. 전체 개수
        long totalCount = (categoryId == 0)
                ? eventAnnMapper.selectAnnTotalCount(searchValue)
                : eventAnnMapper.selectAnnCategoryCount(categoryId, searchValue);

        // 3. DB 조회 (LIMIT X OFFSET)
        int offset = (int) pageable.getOffset();
        List<EventAnnListResponse> list = eventAnnMapper.selectAnnEventList(categoryId, searchValue, orderType, offset, size);

       // 4. Page 객체로 반환
        return new PageImpl<>(list, pageable, totalCount);
    }

    // 이벤트 당첨발표 게시글 상세 조회
    public EventAnnDetailResponse detail(Integer eventId) {
        Integer userId = SecurityUtil.getCurrentUserId();

        eventAnnMapper.increaseViews(eventId);

        EventAnnDetailResponse response = eventAnnMapper.selectAnnEventDetail(eventId);

        // 좋아요 여부
        boolean liked = eventAnnMapper.isEventLiked(eventId, userId) > 0;
        response.setIsLiked(liked);

        // 이전글 / 다음글
        response.setPrevSeq(eventAnnMapper.selectPrevEventId(response.getCategoryId(), eventId));
        response.setNextSeq(eventAnnMapper.selectNextEventId(response.getCategoryId(), eventId));

        return response;
    }

    // 좋아요
    @Transactional
    public void like(Integer eventId) {
        Integer userId = SecurityUtil.getCurrentUserId();

        if(eventAnnMapper.isEventLiked(eventId, userId) == 0) {
            eventAnnMapper.insertEventLike(eventId, userId);
            eventAnnMapper.increaseEventLikeCount(eventId);
        }
    }

    // 좋아요 취소
    public void unlike(Integer eventId) {
        Integer userId = SecurityUtil.getCurrentUserId();

        if(eventAnnMapper.isEventLiked(eventId, userId) > 0) {
            eventAnnMapper.deleteEventLike(eventId, userId);
            eventAnnMapper.decreaseEventLikeCount(eventId);
        }
    }

    // 이벤트 당첨발표 게시글 수정
    public void update(Integer eventId, EventAnnRequest request) {
        Integer userId = SecurityUtil.getCurrentUserId();

        int updated = eventAnnMapper.updateAnnEvent(eventId, userId, request);
        if (updated == 0) {
            throw new IllegalStateException("수정 권한이 없거나 게시글이 존재하지 않습니다.");
        }
    }

    // 이벤트 당첨발표 게시글 삭제
    public void delete(Integer eventId) {
        Integer userId = SecurityUtil.getCurrentUserId();

        int deleted = eventAnnMapper.deleteAnnEvent(eventId, userId);
        if (deleted == 0) {
            throw new IllegalStateException("삭제 권한이 없거나 게시글이 존재하지 않습니다.");
        }
    }

}

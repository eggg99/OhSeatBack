package com.ohseat.ohseatback.domain.event.service;

import com.ohseat.ohseatback.domain.common.service.ViewCountService;
import com.ohseat.ohseatback.domain.event.dto.*;
import com.ohseat.ohseatback.domain.event.mapper.EventAnnMapper;
import com.ohseat.ohseatback.security.SecurityUtil;
import com.ohseat.ohseatback.utils.CustomPageUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    private final ViewCountService viewCountService;
    private final EventInteractionService eventInteractionService;

    // 이벤트 당첨발표 게시글 등록
    public EventAnnWriteResponse create(EventAnnRequest request) {
        eventAnnMapper.insertAnnEvent(request);
        return new EventAnnWriteResponse(request.getEventId());
    }

    // 이벤트 당첨발표 전체 게시글 조회
    public Page<EventAnnListResponse> list(Integer categoryId, Integer searchType, String searchValue, String orderType, int page, int size) {
        // 1. Pageable 생성
        Pageable pageable = CustomPageUtils.getPageable(page, size);

        // 2. 전체 개수
        long totalCount = (categoryId == 0)
                ? eventAnnMapper.selectAnnTotalCount(searchType, searchValue)
                : eventAnnMapper.selectAnnCategoryCount(categoryId, searchType, searchValue);

        // 3. DB 조회 (LIMIT X OFFSET)
        int offset = (int) pageable.getOffset();
        List<EventAnnListResponse> list = eventAnnMapper.selectAnnEventList(categoryId, searchType, searchValue, orderType, offset, size);

       // 4. Page 객체로 반환
        return new PageImpl<>(list, pageable, totalCount);
    }

    // 이벤트 당첨발표 게시글 상세 조회
    public EventAnnDetailResponse detail(Integer eventId, HttpServletRequest request, HttpServletResponse response) {
        Integer userId = SecurityUtil.getCurrentUserId();

        // 최초 조회
        EventAnnDetailResponse responseDto = eventAnnMapper.selectAnnEventDetail(eventId);

        // 조회수 증가
        if (viewCountService.canIncrease("event_ann", eventId, userId, responseDto.getAuthorId(), request, response)) {
            eventAnnMapper.increaseViews(eventId);
        }

        // 증가 반영된 데이터 재조회
        responseDto = eventAnnMapper.selectAnnEventDetail(eventId);

        // 좋아요 여부
        boolean liked = eventInteractionService.isLiked(eventId, userId);
        responseDto.setIsLiked(liked);

        // 이전글 / 다음글
        responseDto.setPrevSeq(eventAnnMapper.selectPrevEventId(responseDto.getCategoryId(), eventId));
        responseDto.setNextSeq(eventAnnMapper.selectNextEventId(responseDto.getCategoryId(), eventId));

        return responseDto;
    }

    // 좋아요
    @Transactional
    public void like(Integer eventId) {
        Integer userId = SecurityUtil.getCurrentUserId();

        eventInteractionService.like(eventId, userId);
    }

    // 좋아요 취소
    public void unlike(Integer eventId) {
        Integer userId = SecurityUtil.getCurrentUserId();

        eventInteractionService.unlike(eventId, userId);
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

package com.ohseat.ohseatback.domain.event.service;

import com.ohseat.ohseatback.domain.event.dto.*;
import com.ohseat.ohseatback.domain.event.mapper.EventAnnMapper;
import com.ohseat.ohseatback.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
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
    public EventAnnListWrapperResponse list(Integer categoryId, int page, int size) {
        int totalCount;
        if (categoryId == 0) {
            totalCount = eventAnnMapper.selectAnnTotalCount();
        } else {
            totalCount = eventAnnMapper.selectAnnCategoryCount(categoryId);
        }

        int offset = page * size;

        List<EventAnnListResponse> list = eventAnnMapper.selectAnnEventList(categoryId, offset, size);

        EventAnnListWrapperResponse response = new EventAnnListWrapperResponse();
        response.setTotalCount(totalCount);
        response.setList(list);

        return response;
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

package com.ohseat.ohseatback.domain.event.service;

import com.ohseat.ohseatback.domain.event.dto.*;
import com.ohseat.ohseatback.domain.event.mapper.EventAnnMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public List<EventAnnListResponse> list() {
        return eventAnnMapper.selectAnnEventList();
    }

    // 이벤트 당첨발표 게시글 상세 조회
    public EventAnnDetailResponse detail(Integer eventId) {
        eventAnnMapper.increaseViews(eventId);
        return eventAnnMapper.selectAnnEventDetail(eventId);
    }

    // 이벤트 당첨발표 게시글 수정
    public void update(Integer eventId, EventAnnRequest request) {
        eventAnnMapper.updateAnnEvent(eventId, request);
    }

    // 이벤트 당첨발표 게시글 삭제
    public void delete(Integer eventId) {
        eventAnnMapper.deleteAnnEvent(eventId);
    }

}

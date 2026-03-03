package com.ohseat.ohseatback.domain.event.mapper;

import com.ohseat.ohseatback.domain.event.dto.EventDetailResponseDTO;
import com.ohseat.ohseatback.domain.event.dto.EventListResponseDTO;
import com.ohseat.ohseatback.domain.event.dto.EventMainResponseDTO;
import com.ohseat.ohseatback.domain.event.entity.Event;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface EventMapper {

    // 메인 이벤트 조회 (진행중+ 랜덤)
    List<EventMainResponseDTO> selectRandomOngoingEvents(@Param("count") int count);

    // 이벤트 전체 조회
    List<EventListResponseDTO> selectEventList(@Param("categoryId") Integer categoryId, @Param("searchType") Integer searchType, @Param("searchValue") String searchValue, @Param("orderType") String orderType, @Param("offset") int offset, @Param("size") int size);

    // 전체 게시글 개수
    int selectEventTotalCount(@Param("searchType") Integer searchType, @Param("searchValue") String searchValue);

    // 카테고리별 게시글 개수
    int selectEventCategoryCount(@Param("categoryId") Integer categoryId, @Param("searchType") Integer searchType, @Param("searchValue") String searchValue);

    // 이벤트 상세 조회
    EventDetailResponseDTO selectEventDetail(@Param("eventId") int eventId);

    // 조회수 증가
    void increaseViews(@Param("eventId") int eventId);

    // 이전글
    Integer selectPrevEventId(@Param("eventId") int eventId, @Param("categoryId") Integer categoryId);

    // 다음글
    Integer selectNextEventId(@Param("eventId") int eventId, @Param("categoryId") Integer categoryId);

    // 게시글 작성
    void insertEvent(Event event);

    // 게시글 수정
    void updateEvent(Event event);

    // 게시글 삭제
    void deleteEvent(@Param("eventId") int eventId);
}

package com.ohseat.ohseatback.domain.event.mapper;

import com.ohseat.ohseatback.domain.event.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface EventAnnMapper {

    int insertAnnEvent(EventAnnRequest request);

    // ANN 리스트 조회
    List<EventAnnListResponse> selectAnnEventList(@Param("categoryId") Integer categoryId, @Param("searchValue") String searchValue, @Param("orderType") String orderType, @Param("offset") int offset, @Param("size") int size);

    // 전체 게시글 개수
    int selectAnnTotalCount(@Param("searchValue") String searchValue);

    // 카테고리별 게시글 개수
    int selectAnnCategoryCount(@Param("categoryId") Integer categoryId, @Param("searchValue") String searchValue);

    // ANN 상세 조회
    EventAnnDetailResponse selectAnnEventDetail(@Param("eventId") Integer eventId);
    int increaseViews(@Param("eventId") Integer eventId);

    Integer selectPrevEventId(@Param("categoryId") Integer categoryId, @Param("eventId") Integer eventId);
    Integer selectNextEventId(@Param("categoryId") Integer categoryId, @Param("eventId") Integer eventId);

    // 좋아요
    int isEventLiked(@Param("eventId")Integer eventId, @Param("userId") Integer userId);

    void insertEventLike(@Param("eventId") Integer eventId, @Param("userId") Integer userId);
    void deleteEventLike(@Param("eventId") Integer eventId, @Param("userId") Integer userId);

    void increaseEventLikeCount(@Param("eventId") Integer eventId);
    void decreaseEventLikeCount(@Param("eventId") Integer eventId);

    // ANN 수정
    int updateAnnEvent(@Param("eventId") Integer eventId, @Param("authorId") Integer authorId, @Param("request") EventAnnRequest request);

    // ANN 삭제
    int deleteAnnEvent(@Param("eventId") Integer eventId, @Param("authorId") Integer authorId);

}

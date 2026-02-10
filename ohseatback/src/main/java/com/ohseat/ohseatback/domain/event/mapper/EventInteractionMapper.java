package com.ohseat.ohseatback.domain.event.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EventInteractionMapper {

    // 좋아요
    int isEventLiked(@Param("eventId") Integer eventId, @Param("userId") Integer userId);

    void insertEventLike(@Param("eventId") Integer eventId, @Param("userId") Integer userId);

    void deleteEventLike(@Param("eventId") Integer eventId, @Param("userId") Integer userId);

    void increaseEventLikeCount(@Param("eventId") Integer eventId);

    void decreaseEventLikeCount(@Param("eventId") Integer eventId);
}

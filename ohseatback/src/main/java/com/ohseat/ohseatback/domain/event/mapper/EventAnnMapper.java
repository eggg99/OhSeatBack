package com.ohseat.ohseatback.domain.event.mapper;

import com.ohseat.ohseatback.domain.event.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventAnnMapper {

    int insertAnnEvent(EventAnnRequest request);

    List<EventAnnListResponse> selectAnnEventList();

    EventAnnDetailResponse selectAnnEventDetail(@Param("eventId") Integer eventId);

    int updateAnnEvent(@Param("eventId") Integer eventId, @Param("request") EventAnnRequest request);

    int deleteAnnEvent(@Param("eventId") Integer eventId);

    int increaseViews(@Param("eventId") Integer eventId);

}

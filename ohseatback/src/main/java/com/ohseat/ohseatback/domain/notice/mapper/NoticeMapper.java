package com.ohseat.ohseatback.domain.notice.mapper;

import com.ohseat.ohseatback.domain.notice.dto.NoticeDetailResponse;
import com.ohseat.ohseatback.domain.notice.dto.NoticeListResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeMapper {

    void insertNotice(@Param("targetBoard") String targetBoard, @Param("authorId") Integer authorId, @Param("title") String title, @Param("content") String content);

    List<NoticeListResponse> selectActiveNotices(String targetBoard);

    NoticeDetailResponse selectNoticeDetail(Long noticeId);

    void increaseViews(Long noticeId);

    void deactiveNotice(Long noticeId); // is_active = 0

}

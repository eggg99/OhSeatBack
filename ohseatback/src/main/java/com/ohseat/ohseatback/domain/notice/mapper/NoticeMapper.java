package com.ohseat.ohseatback.domain.notice.mapper;

import com.ohseat.ohseatback.domain.notice.dto.NoticeAdminListResponse;
import com.ohseat.ohseatback.domain.notice.dto.NoticeDetailResponse;
import com.ohseat.ohseatback.domain.notice.dto.NoticeListResponse;
import com.ohseat.ohseatback.domain.notice.dto.NoticeUpdateRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeMapper {

    // 공지사항 작성
    void insertNotice(@Param("targetBoard") String targetBoard, @Param("authorId") Integer authorId, @Param("title") String title, @Param("content") String content);

    // 관리자 공지사항 전체 조회
    List<NoticeAdminListResponse> selectAdminNoticeList(String targetBoard);

    // 공지사항 전체 조회
    List<NoticeListResponse> selectTopPinnedNotices(String targetBoard);

    // 공지사항 상세 조회
    NoticeDetailResponse selectNoticeDetail(Long noticeId);

    // 조회수 증가
    void increaseViews(Long noticeId);

    // 공지사항 수정
    int updateNotice(@Param("noticeId") Long noticeId, @Param("request") NoticeUpdateRequest request);

    // 공지사항 삭제 (비노출)
    void deactiveNotice(Long noticeId); // is_active = 0

    // 고정 활성 / 비활성 업데이트
    int updateActiveStatus(@Param("noticeId") Long noticeId, @Param("isActive") boolean isActive);

    // 고정 / 해제 토글
    // 1-1. 현재 고정 여부 조회
    Integer selectPinnedStatus(Long noticeId);

    // 1-2. 고정 공지 개수 조회 (board별)
    int countPinnedNotices(String targetBoard);

    // 1-3. 고정 / 해제 토글
    int updatePinnedStatus(@Param("noticeId") Long noticeId, @Param("isPinned") int isPinned);

    // 1-4. noticeId -> targetBoard 조회
    String selectTargetBoardByNoticeId(Long noticeId);

}

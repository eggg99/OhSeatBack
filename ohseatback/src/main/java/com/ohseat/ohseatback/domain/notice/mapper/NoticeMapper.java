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
    List<NoticeAdminListResponse> selectAdminNoticeList(@Param("targetBoard") String targetBoard, @Param("offset") int offset, @Param("limit") int limit);

    // 전체 공지글 개수
    int selectAdminNoticeCount(String targetBoard);

    // 공지사항 전체 조회
    List<NoticeListResponse> selectTopPinnedNotices(String targetBoard);

    // 공지사항 상세 조회
    NoticeDetailResponse selectNoticeDetail(Integer noticeId);

    // 조회수 증가
    void increaseViews(Integer noticeId);

    // 공지사항 수정
    int updateNotice(@Param("noticeId") Integer noticeId, @Param("request") NoticeUpdateRequest request);

    // 공지사항 삭제 (비노출)
    void deactiveNotice(Integer noticeId); // is_active = 0

    // 고정 활성 / 비활성 업데이트
    int updateActiveStatus(@Param("noticeId") Integer noticeId, @Param("isActive") boolean isActive);

    // 고정 / 해제 토글
    // 1-1. 현재 고정 여부 조회
    Integer selectPinnedStatus(Integer noticeId);

    // 1-2. 고정 공지 개수 조회 (board별)
    int countPinnedNotices(String targetBoard);

    // 1-3. 고정 / 해제 토글
    int updatePinnedStatus(@Param("noticeId") Integer noticeId, @Param("isPinned") int isPinned);

    // 1-4. noticeId -> targetBoard 조회
    String selectTargetBoardByNoticeId(Integer noticeId);

}

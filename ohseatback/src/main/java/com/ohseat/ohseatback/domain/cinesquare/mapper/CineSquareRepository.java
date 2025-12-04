package com.ohseat.ohseatback.domain.cinesquare.mapper;

import com.ohseat.ohseatback.domain.cinesquare.dto.CommentDTO;
import com.ohseat.ohseatback.domain.cinesquare.entity.CineSquare;
import com.ohseat.ohseatback.domain.cinesquare.entity.CommentDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

//MyBatis 인터페이스
@Mapper
public interface CineSquareRepository {
    // 게시글 등록
    void insertPost(CineSquare post);

    // 단건 조회
    CineSquare selectPostById(Integer postId);

    // 조회수 증가
    void increaseViewCount(Integer postId);

    // 카테고리별 게시글 무한 스크롤 조회
    List<CineSquare> selectPostsByScroll(
            @Param("categoryId") Integer categoryId,
            @Param("lastPostId") Integer lastPostId,
            @Param("limit") int limit,
            @Param("orderBy") String orderBy
    );

    // 전체 인기글 조회 (일주일 기준)
    List<CineSquare> selectWeeklyRanking(LocalDateTime startDate, LocalDateTime endDate, int limit);

    // 게시글 수정
    void updatePost(CineSquare post);

    // 게시글 삭제
    void deletePost(Integer postId, Integer authorId);

    // 댓글
    List<CommentDTO> getCommentList(Integer postId);
    CommentDomain getCommentById(Integer commentId);
    void insertComment(Integer commenterId, Integer postId, String content);
    void updateComment(Integer commentId, Integer commenterId, String content);
    void deleteComment(Integer commentId);

    // 좋아요
    int isPostLiked(Integer postId, Integer userId);
    void insertPostLike(Integer postId, Integer userId);
    void deletePostLike(Integer postId, Integer userId);
    void increasePostLikeCount(Integer postId);
    void decreasePostLikeCount(Integer postId);

    // 댓글, 좋아요 카운트
    int countCommentsByPostId(Integer postId);
    int countLikesByPostId(Integer postId);

    // 이전 글 / 다음 글
    Integer selectPrevPostId(Integer categoryId, Integer postId);
    Integer selectNextPostId(Integer categoryId, Integer postId);

    // 관리자 글 작성 / 삭제
    void insertNoticePost(CineSquare post);
    void deletePostByAdmin(Integer postId);
}

package com.ohseat.ohseatback.domain.recommend.mapper;

import com.ohseat.ohseatback.domain.recommend.entity.CinemaEntity;
import com.ohseat.ohseatback.domain.recommend.entity.CommentDomain;
import com.ohseat.ohseatback.domain.recommend.entity.PostDomain;
import com.ohseat.ohseatback.domain.user.entity.User;
import com.ohseat.ohseatback.domain.recommend.dto.CinemaDTO;
import com.ohseat.ohseatback.domain.recommend.dto.ScreenDTO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface RecommendMapper {

    List<CinemaDTO> getTrendingCinema();

    // 영화관 리스트 조회
    List<CinemaEntity> getCinemaList(Integer multiplexId, Integer areaId);

    // 상영관 리스트 조회
    List<ScreenDTO> getScreenList(Integer multiplexId, String cinemaId);

    // 게시글 리스트 조회
    List<PostDomain> getPostList(
            @Param("multiplexId") Integer multiplexId,
            @Param("areaId") String areaId,
            @Param("cinemaId") String cinemaId,
            @Param("screenId") String screenId,
            @Param("limit") int limit,
            @Param("offset") long offset
    );

    long countPosts(
            @Param("cinemaId") String cinemaId,
            @Param("screenId") String screenId
    );

    // 유저 정보 조회
    List<User> getUserList(@Param("userIds") List<Integer> userIds);

    // 게시글 댓글 개수 조회
    @MapKey("postIds")
    List<Map<String, Object>> getCommentCountMap(@Param("postIds") List<Integer> postIds);


    // 게시글 상세 조회
    PostDomain getPostDetail(Integer postId);
    // 유저 닉네임 조회
    User getUser(Integer userId);
    // 댓글 개수 조회
    Long getCommentCount(Integer postId);
    // 댓글 리스트 조회
    List<CommentDomain> getCommentList(@Param("postId") Integer postId);
    // 댓글 입력
    void putComment(Integer commenterId, Integer postId, String content);

    // 포스트 등록
    void putPost(PostDomain domain);
    // 포스트 업데이트
    void updatePost(Integer multiplexId, Integer areaId, String cinemaId, String screenId, String title, String content, Integer postId);
    // 포스트 삭제
    void deletePost(Integer postId);
    // 댓글 삭제
    void deleteComment(Integer commentId);
    // 조회수 증가
    void incrementViewCount(Integer postId);
    // 유저의 좋아요 여부
    int isPostLike(Integer postId, Integer userId);
    // 좋아요 추가
    void insertPostLike(Integer postId, Integer userId);
    void insertPostLikeCount(Integer postId);
    // 좋아요 취소
    void deletePostLike(Integer postId, Integer userId);
    void deletePostLikeCount(Integer postId);
}

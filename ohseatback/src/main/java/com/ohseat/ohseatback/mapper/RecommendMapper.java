package com.ohseat.ohseatback.mapper;

import com.ohseat.ohseatback.domain.Cinema;
import com.ohseat.ohseatback.domain.CommentDomain;
import com.ohseat.ohseatback.domain.PostDomain;
import com.ohseat.ohseatback.domain.User;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface RecommendMapper {
    // 영화관 리스트 조회
    List<Cinema> getCinemaList(Integer multiplexId, Integer areaId);

    // 상영관 리스트 조회
    List<Cinema> getScreenList(Integer multiplexId, String cinemaId);

    // 게시글 리스트 조회
    List<PostDomain> getPostList(
            @Param("cinemaId") String cinemaId,
            @Param("screenId") String screenId,
            @Param("limit") int limit,
            @Param("offset") int offset
    );

    long countPosts(
            @Param("cinemaId") String cinemaId,
            @Param("screenId") String screenId
    );

    // 유저 정보 조회
    List<User> getUserList(@Param("userIds") List<Integer> userIds);

    // 게시글 댓글 개수 조회
    List<Map<String, Object>> getCommentCountMap(@Param("postIds") List<Integer> postIds);

    // 댓글 리스트 조회
    List<CommentDomain> getCommentList(@Param("postIds") List<Integer> postIds);
}

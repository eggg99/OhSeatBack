package com.ohseat.ohseatback.domain.cinesquare.mapper;

import com.ohseat.ohseatback.domain.cinesquare.entity.CineSquare;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    // 게시글 수정
    void updatePost(CineSquare post);

    // 게시글 삭제
    void deletePost(Integer postId, Integer authorId);
}

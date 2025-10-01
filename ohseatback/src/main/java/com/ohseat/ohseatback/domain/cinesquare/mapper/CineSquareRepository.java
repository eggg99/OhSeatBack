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
    
    // 카테고리별 게시글 조회 (페이징 + 정렬)
    List<CineSquare> selectPostsByCategory(
            @Param("categoryId") Integer categoryId,
            @Param("limit") int limit,
            @Param("offset") int offset,
            @Param("orderBy") String orderBy
    );
    
    // 전체 글 개수 조회
    int countPostsByCategory(@Param("categoryId") Integer categoryId);
    
    // 게시글 수정
    void updatePost(CineSquare post);
    
    // 게시글 삭제
    void deletePost(Integer postId, Integer authorId);
}

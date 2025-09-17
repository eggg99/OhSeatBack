package com.ohseat.ohseatback.repository;

import com.ohseat.ohseatback.domain.CineSquare;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CineSquareRepository {
    void insertPost(CineSquare post);
    CineSquare selectPostById(Integer postId);
    List<CineSquare> selectAllPosts(String category);
    void updatePost(CineSquare post);
    void deletePost(Integer postId, Integer authorId);
}

package com.ohseat.ohseatback.service;

import com.ohseat.ohseatback.domain.CineSquare;
import com.ohseat.ohseatback.dto.CineSquareResponse;
import com.ohseat.ohseatback.mapper.CineSquareMapper;
import com.ohseat.ohseatback.repository.CineSquareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CineSquareService {

    private final CineSquareRepository cineSquareRepository;
    private final CineSquareMapper cineSquareMapper;

    public void createPost(CineSquare post) { cineSquareRepository.insertPost(post); }

    public CineSquare getPost(Integer postId) {
        return cineSquareRepository.selectPostById(postId);
    }

    public Page<CineSquareResponse> getAllPosts(Integer categoryId, String orderType, int page, int size) {
        int offset = (page - 1) * size;

        // orderType에 따라 정렬 컬럼 결정
        String orderBy = "cs.created_at DESC"; // default 최신순
        if ("views".equals(orderType)) {
            orderBy = "cs.views DESC";
        }
        // 댓글순
//        else if ("comments".equals(orderType)) {
//            orderBy = "cs.comments DESC";
//        }

        // DB 조회
        List<CineSquare> posts = cineSquareRepository.selectPostsByCategory(categoryId, size, offset, orderBy);

        // 전체 글 개수 조회
        int total = cineSquareRepository.countPostsByCategory(categoryId);

        // DTO 변환
        List<CineSquareResponse> dtoList = posts.stream()
                .map(cineSquareMapper::toResponseDto)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, PageRequest.of(page - 1, size), total);

//        return cineSquareRepository.selectAllPosts(categoryId);
    }

    public void updatePost(CineSquare post) {
        cineSquareRepository.updatePost(post);
    }

    public void deletePost(Integer postId, Integer authorId) {
        cineSquareRepository.deletePost(postId, authorId);
    }

}

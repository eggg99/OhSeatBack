package com.ohseat.ohseatback.domain.cinesquare.service;

import com.ohseat.ohseatback.domain.cinesquare.dto.LocationResponse;
import com.ohseat.ohseatback.domain.cinesquare.entity.CineSquare;
import com.ohseat.ohseatback.domain.cinesquare.dto.CineSquareResponse;
import com.ohseat.ohseatback.domain.cinesquare.mapper.CineSquareMapper;
import com.ohseat.ohseatback.domain.cinesquare.mapper.CineSquareRepository;
import jakarta.transaction.Transactional;
import com.ohseat.ohseatback.utils.LocationUtils;
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
    private final LocationUtils locationUtils;

    // 게시글 등록
    public void createPost(CineSquare post) { cineSquareRepository.insertPost(post); }

    // 단건 조회
    @Transactional
    public CineSquare getPost(Integer postId) {
        // 1) 존재 여부 확인
        CineSquare post = cineSquareRepository.selectPostById(postId);
        if (post == null) {
            return null;
        }

        // 2) 조회수 증가
        cineSquareRepository.increaseViewCount(postId);

        // 3) 증가 반영된 데이터 재조회하여 반환
        return cineSquareRepository.selectPostById(postId);
    }

    // 카테고리별 게시글 무한 스크롤 조회
    public List<CineSquare> getPostsByScroll(Integer categoryId, Integer lastPostId, int limit, String orderType) {

        // orderType에 따라 정렬 컬럼 결정
        String orderBy = "cs.created_at DESC"; // default 최신순
        if ("views".equals(orderType)) {
            orderBy = "cs.views DESC";
        }
        // 댓글순
//        else if ("comments".equals(orderType)) {
//            orderBy = "cs.comments DESC";
//        }

        return cineSquareRepository.selectPostsByScroll(categoryId, lastPostId, limit, orderBy);

    }

    // 게시글 수정
    public void updatePost(CineSquare post) {
        cineSquareRepository.updatePost(post);
    }

    // 게시글 삭제
    public void deletePost(Integer postId, Integer authorId) {
        cineSquareRepository.deletePost(postId, authorId);
    }

    public LocationResponse getLocation (double longitude, double latitude) {
        return locationUtils.getLocation(longitude, latitude);
    }

    public LocationResponse searchLocation (String searchValue) {
        return locationUtils.searchLocation(searchValue);
    }

}

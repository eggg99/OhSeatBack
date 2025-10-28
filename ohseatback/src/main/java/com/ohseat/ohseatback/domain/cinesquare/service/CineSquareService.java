package com.ohseat.ohseatback.domain.cinesquare.service;

import com.ohseat.ohseatback.domain.cinesquare.dto.LocationResponse;
import com.ohseat.ohseatback.domain.cinesquare.entity.CineSquare;
import com.ohseat.ohseatback.domain.cinesquare.dto.CineSquareResponse;
import com.ohseat.ohseatback.domain.cinesquare.mapper.CineSquareMapper;
import com.ohseat.ohseatback.domain.cinesquare.mapper.CineSquareRepository;
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

    public void createPost(CineSquare post) { cineSquareRepository.insertPost(post); }

    public CineSquare getPost(Integer postId) {
        return cineSquareRepository.selectPostById(postId);
    }

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

    public void updatePost(CineSquare post) {
        cineSquareRepository.updatePost(post);
    }

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

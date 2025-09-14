package com.ohseat.ohseatback.service;

import com.ohseat.ohseatback.domain.Cinema;
import com.ohseat.ohseatback.domain.CommentDomain;
import com.ohseat.ohseatback.domain.PostDomain;
import com.ohseat.ohseatback.domain.User;
import com.ohseat.ohseatback.dto.recommend.PostListDTO;
import com.ohseat.ohseatback.mapper.RecommendMapper;
import com.ohseat.ohseatback.utils.CustomPageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendService {
    private final RecommendMapper recommendMapper;

    // 영화관 리스트 조회
    public List<Cinema> getCinemaList(Integer multiplexId, Integer areaId) {
        return recommendMapper.getCinemaList(multiplexId, areaId);
    }

    // 상영관 리스트 조회
    public List<Cinema> getScreenList(Integer multiplexId, String cinemaId) {
        return recommendMapper.getScreenList(multiplexId, cinemaId);
    }

    // 게시글 리스트 조회 (페이징)
    public Page<PostListDTO> getPostList(String cinemaId, String screenId, String orderType, int page, int size) {
        // 1. Pageable 생성 (CustomPageUtils 활용)
        Pageable pageable = CustomPageUtils.getPageable(page, size);

        // 2. DB 조회
        List<PostDomain> postList = recommendMapper.getPostList(
                cinemaId,
                screenId,
                pageable.getPageSize(),
                pageable.getOffset()
        );

        // 3. User 매핑
        Set<Integer> userIds = postList.stream()
                .map(PostDomain::getAuthorId)
                .collect(Collectors.toSet());

        List<User> users = userIds.isEmpty()
                ? Collections.emptyList()
                : recommendMapper.getUserList(new ArrayList<>(userIds));

        Map<Integer, User> userMap = users.stream()
                .collect(Collectors.toMap(User::getUserId, u -> u));

        // 4. Comment count 매핑
        List<Integer> postIds = postList.stream()
                .map(PostDomain::getPostId)
                .collect(Collectors.toList());

        List<Map<String, Object>> commentCountList = postIds.isEmpty()
                ? Collections.emptyList()
                : recommendMapper.getCommentCountMap(postIds);

        Map<Integer, Integer> commentCountMap = commentCountList.stream()
                .collect(Collectors.toMap(
                        map -> ((Long) map.get("post_id")).intValue(),
                        map -> ((Long) map.get("commentCount")).intValue()
                ));


        // 5. Domain → DTO 변환
        List<PostListDTO> dtoList = postList.stream()
                .map(post -> {
                    PostListDTO dto = new PostListDTO();
                    dto.setPostId(post.getPostId());
                    dto.setTitle(post.getTitle());
                    dto.setContent(post.getContent());
                    dto.setViews(post.getViews());
                    dto.setCreatedAt(post.getCreatedAt());
                    dto.setAuthorNickname(
                            Optional.ofNullable(userMap.get(post.getAuthorId()))
                                    .map(User::getNickname)
                                    .orElse(null)
                    );
                    dto.setCommentCount(Long.valueOf(commentCountMap.getOrDefault(post.getPostId(), 0)));
                    return dto;
                })
                .collect(Collectors.toList());

        // 6. 정렬
        Comparator<PostListDTO> comparator;
        switch (orderType) {
            case "views":
                comparator = Comparator.comparing(PostListDTO::getViews).reversed();
                break;
            case "comments":
                comparator = Comparator.comparing(PostListDTO::getCommentCount).reversed();
                break;
            case "latest":
            default:
                comparator = Comparator.comparing(PostListDTO::getCreatedAt).reversed();
        }
        dtoList.sort(comparator);

        // 7. Page 객체로 반환 (page 계산은 CustomPageUtils에 맡김)
        long totalCount = recommendMapper.countPosts(cinemaId, screenId);
        return new PageImpl<>(dtoList, pageable, totalCount);
    }
}

package com.ohseat.ohseatback.domain.recommend.service;

import com.ohseat.ohseatback.domain.recommend.entity.CommentDomain;
import com.ohseat.ohseatback.domain.recommend.entity.PostDomain;
import com.ohseat.ohseatback.domain.user.entity.User;
import com.ohseat.ohseatback.domain.recommend.dto.CinemaDTO;
import com.ohseat.ohseatback.domain.recommend.dto.CommentDTO;
import com.ohseat.ohseatback.domain.recommend.dto.PostDTO;
import com.ohseat.ohseatback.domain.recommend.dto.ScreenDTO;
import com.ohseat.ohseatback.domain.recommend.mapper.RecommendMapper;
import com.ohseat.ohseatback.utils.CustomPageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
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
    public List<CinemaDTO> getCinemaList(Integer multiplexId, Integer areaId) {
        return recommendMapper.getCinemaList(multiplexId, areaId);
    }

    // 상영관 리스트 조회
    public List<ScreenDTO> getScreenList(Integer multiplexId, String cinemaId) {
        return recommendMapper.getScreenList(multiplexId, cinemaId);
    }

    // 게시글 리스트 조회 (페이징)
    public Page<PostDTO> getPostList(Integer multiplexId, Integer areaId, String cinemaId, String screenId, String orderType, int page, int size) {
        // 1. Pageable 생성 (CustomPageUtils 활용)
        Pageable pageable = CustomPageUtils.getPageable(page, size);

        // 2. DB 조회
        List<PostDomain> postList = recommendMapper.getPostList(
                multiplexId,
                areaId,
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
        List<PostDTO> dtoList = postList.stream()
                .map(post -> {
                    PostDTO dto = new PostDTO();
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
        Comparator<PostDTO> comparator;
        switch (orderType) {
            case "views":
                comparator = Comparator.comparing(PostDTO::getViews).reversed();
                break;
            case "comments":
                comparator = Comparator.comparing(PostDTO::getCommentCount).reversed();
                break;
            case "latest":
            default:
                comparator = Comparator.comparing(PostDTO::getCreatedAt).reversed();
        }
        dtoList.sort(comparator);

        // 7. Page 객체로 반환 (page 계산은 CustomPageUtils에 맡김)
        long totalCount = recommendMapper.countPosts(cinemaId, screenId);
        return new PageImpl<>(dtoList, pageable, totalCount);
    }

    public PostDTO getPostDetail(Integer postId) {
        // 1. 게시글 1개 조회
        PostDomain post = recommendMapper.getPostDetail(postId);

        // 2. 작성자 1명 조회
        User author = recommendMapper.getUser(post.getAuthorId());

        // 3. 댓글 수 조회 (이미 commentCountMap 같은 걸 사용한다면)
        Long commentCount = recommendMapper.getCommentCount(post.getPostId());

        // 4. DTO 생성
        PostDTO dto = new PostDTO();
        dto.setPostId(post.getPostId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setViews(post.getViews());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setAuthorNickname(author != null ? author.getNickname() : null);
        dto.setCommentCount(commentCount);
        dto.setMultiplexId(post.getMultiplexId());
        dto.setAreaId(post.getAreaId());
        dto.setCinemaId(post.getCinemaId());
        dto.setScreenId(post.getScreenId());
        dto.setAuthorId(String.valueOf(post.getAuthorId()));
        return dto;
    }

    public List<CommentDTO> getCommentList(Integer postId) {
        List<CommentDomain> commentDomain = recommendMapper.getCommentList(postId);

        // 1. 모든 댓글 작성자 ID 수집
        List<Integer> commenterIds = commentDomain.stream()
                .map(CommentDomain::getCommenterId)
                .distinct() // 중복 제거
                .collect(Collectors.toList());

        // 2. 한 번에 유저 조회 (IN 절 사용)
        List<User> users = recommendMapper.getUserList(commenterIds);
        Map<Integer, User> userMap = users.stream()
                .collect(Collectors.toMap(User::getUserId, user -> user));

        List<CommentDTO> dtoList = commentDomain.stream()
                .map(comment -> {
                    CommentDTO dto = new CommentDTO();
                    dto.setCommentId(comment.getCommentId());
                    dto.setPostId(comment.getPostId());
                    dto.setCommenterId(comment.getCommenterId());
                    dto.setContent(comment.getContent());
                    dto.setCreatedAt(comment.getCreatedAt());

                    User user = userMap.get(comment.getCommenterId());
                    if (user != null) {
                        dto.setAuthorNickname(user.getNickname());
                    }

                    return dto;
                })
                .collect(Collectors.toList());
        return dtoList;
    }

    public void putComment(Integer commenterId, Integer postId, String content) {
        recommendMapper.putComment(commenterId, postId, content);
    }

    public void putPost(Integer userId, Integer multiplexId, Integer areaId, String cinemaId, String screenId, String title, String content) {
        recommendMapper.putPost(userId, multiplexId, areaId, cinemaId, screenId, title, content);
    }

    public void updatePost(Integer multiplexId, Integer areaId, String cinemaId, String screenId, String title, String content, Integer postId) {
        recommendMapper.updatePost(multiplexId, areaId, cinemaId, screenId, title, content, postId);
    }

    public void deletePost(Integer postId) {
        recommendMapper.deletePost(postId);
    }

    public void deleteComment(Integer commentId) {
        recommendMapper.deleteComment(commentId);
    }

}

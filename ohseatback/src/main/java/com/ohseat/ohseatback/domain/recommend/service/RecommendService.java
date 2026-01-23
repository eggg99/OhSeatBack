package com.ohseat.ohseatback.domain.recommend.service;

import com.ohseat.ohseatback.domain.common.policy.PostDeletePolicy;
import com.ohseat.ohseatback.domain.common.service.ViewCountService;
import com.ohseat.ohseatback.domain.recommend.entity.CinemaEntity;
import com.ohseat.ohseatback.domain.recommend.entity.CommentDomain;
import com.ohseat.ohseatback.domain.recommend.entity.PostDomain;
import com.ohseat.ohseatback.domain.user.entity.User;
import com.ohseat.ohseatback.domain.recommend.dto.CinemaDTO;
import com.ohseat.ohseatback.domain.recommend.dto.CommentDTO;
import com.ohseat.ohseatback.domain.recommend.dto.PostDTO;
import com.ohseat.ohseatback.domain.recommend.dto.ScreenDTO;
import com.ohseat.ohseatback.domain.recommend.mapper.RecommendMapper;
import com.ohseat.ohseatback.security.SecurityUtil;
import com.ohseat.ohseatback.utils.CustomPageUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    private final PostDeletePolicy postDeletePolicy;
    private final ViewCountService viewCountService;

    public List<CinemaDTO> getTrendingCinema() {
        return recommendMapper.getTrendingCinema();
    }

    // 영화관 리스트 조회
    public List<CinemaDTO> getCinemaList(Integer multiplexId, Integer areaId) {
        List<CinemaEntity> list = recommendMapper.getCinemaList(multiplexId, areaId);
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }

        // Domain → DTO 변환
        List<CinemaDTO> dtoList = list.stream()
                .map(CinemaDTO::of)
                .toList();

        return dtoList;
    }

    // 상영관 리스트 조회
    public List<ScreenDTO> getScreenList(Integer multiplexId, String cinemaId) {
        return recommendMapper.getScreenList(multiplexId, cinemaId);
    }

    // 게시글 리스트 조회 (페이징)
    public Page<PostDTO> getPostList(Integer multiplexId, String areaId, String cinemaId, String screenId, String orderType, int page, int size) {
        // 1. Pageable 생성 (CustomPageUtils 활용)
        Pageable pageable = CustomPageUtils.getPageable(page, size);

        // 2. DB 조회
        List<PostDomain> postList = recommendMapper.getPostList(
                multiplexId,
                areaId,
                cinemaId,
                screenId
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
                    dto.setMultiplexId(post.getMultiplexId());
                    dto.setCinemaId(post.getCinemaId());
                    dto.setScreenId(post.getScreenId());
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
                    dto.setLikeCount(post.getLikeCount());
                    dto.setMultiplexName(post.getMultiplexName());
                    dto.setCinemaName(post.getCinemaName());
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
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), dtoList.size());
        List<PostDTO> pagedList = dtoList.subList(start, end);

        long totalCount = recommendMapper.countPosts(multiplexId, areaId,cinemaId, screenId);
        return new PageImpl<>(pagedList, pageable, totalCount);
    }

    public PostDTO getPostDetail(Integer postId) {
        // 1. 게시글 1개 조회
        PostDomain post = recommendMapper.getPostDetail(postId);
        if (post == null) {
            throw new RuntimeException("게시글 없음");
        }

        // 2. 작성자 1명 조회
        User author = recommendMapper.getUser(post.getAuthorId());

        // 3. 댓글 수 조회 (이미 commentCountMap 같은 걸 사용한다면)
        Long commentCount = recommendMapper.getCommentCount(post.getPostId());

        // 4. 현재 로그인 유저 좋아요 여부
        int isLikeYn = 0;
        Integer userId = SecurityUtil.getCurrentUserId();
        if(userId != null){
            isLikeYn = recommendMapper.isPostLike(post.getPostId(), userId);
        }

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
        dto.setAuthorId((post.getAuthorId()));
        dto.setLikeCount(post.getLikeCount());
        dto.setLiked(isLikeYn == 1);
        dto.setPrevId(post.getPrevId());
        dto.setNextId(post.getNextId());
        dto.setCinemaName(post.getCinemaName());
        dto.setCinemaAddr(post.getCinemaAddr());
        return dto;
    }

    public List<CommentDTO> getCommentList(Integer postId) {
        List<CommentDomain> commentDomain = recommendMapper.getCommentList(postId);

        if(commentDomain.isEmpty()) {
            return new ArrayList<>(); // 댓글 없으면 빈 리스트 반환
        }

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
                    dto.setUpdatedAt(comment.getUpdatedAt());

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

    public void updateComment(String content, Integer commentId) {
        // 입력한 사용자와 다른 경우 유효성 검사 처리해야함
        recommendMapper.updateComment(content, commentId);
    }

    public Integer putPost(Integer userId, Integer multiplexId, Integer areaId, String cinemaId, String screenId, String title, String content) {
        PostDomain domain = new PostDomain();
        domain.setAuthorId(userId);
        domain.setMultiplexId(multiplexId);
        domain.setAreaId(areaId);
        domain.setCinemaId(cinemaId);
        domain.setScreenId(screenId);
        domain.setTitle(title);
        domain.setContent(content);

        recommendMapper.putPost(domain);
        return domain.getPostId(); // insert 후 MyBatis가 채워줌
    }

    public void updatePost(Integer multiplexId, Integer areaId, String cinemaId, String screenId, String title, String content, Integer postId) {
        recommendMapper.updatePost(multiplexId, areaId, cinemaId, screenId, title, content, postId);
    }

    public void deletePost(Integer postId) {
        Integer currentUserId = SecurityUtil.getCurrentUserId();
        String role = SecurityUtil.getCurrentUserRole();

        PostDomain post = recommendMapper.getPostDetail(postId);
        if (post == null) {
            throw new RuntimeException("게시글 없음");
        }

        // 관리자 / 사용자 체크
        postDeletePolicy.check(post.getAuthorId(), currentUserId, role);

        recommendMapper.deletePost(postId);
    }

    public void deleteComment(Integer commentId) {
        recommendMapper.deleteComment(commentId);
    }

    // 조회수 증가
    public void incrementViewCount(Integer postId, HttpServletRequest request, HttpServletResponse response) {
        Integer userId = SecurityUtil.getCurrentUserId();
        PostDomain post = recommendMapper.getPostDetail(postId);

        if (post == null) return;

        if (viewCountService.canIncrease("recommend", postId, userId, post.getAuthorId(), request, response)) {
            recommendMapper.incrementViewCount(postId);
        }

    }

    // 좋아요 업데이트
    public Map<String, Boolean> updatePostLike (Integer postId) {
        Boolean Liked = false;
        Map<String, Boolean> result = new HashMap<>();
        Integer userId = SecurityUtil.getCurrentUserId();
        if (userId == null) {
            result.put("Liked", false);
            return result; // 로그인 안 되어 있으면 그냥 리턴
        }
        int isLikeYn = recommendMapper.isPostLike(postId, userId);

        boolean liked;

        if (isLikeYn == 0) {    // 좋아요를 누른 적이 없을때 -> 좋아요를 추가
            recommendMapper.insertPostLike(postId, userId);
            recommendMapper.insertPostLikeCount(postId);
            liked = true;
        } else {                // 좋아요를 눌렀을 때 -> 좋아요를 취소
            recommendMapper.deletePostLike(postId, userId);
            recommendMapper.deletePostLikeCount(postId);
            liked = false;
        }

        result.put("Liked", liked);
        return result;

    }

    public List<PostDTO> getPostListRecentTop3() {
        List<PostDomain> domainList = recommendMapper.getPostListRecentTop3();
        List<PostDTO> top3List = domainList.stream().map(post -> {
            PostDTO dto = new PostDTO();
            dto.setPostId(post.getPostId());
            dto.setMultiplexName(post.getMultiplexName());
            dto.setCinemaName(post.getCinemaName());
            dto.setScreenName(post.getScreenName());
            dto.setCinemaId(post.getCinemaId());
            dto.setScreenId(post.getScreenId());
            dto.setAuthorId(post.getAuthorId());
            dto.setContent(post.getContent());
            dto.setCreatedAt(post.getCreatedAt());
                    return dto;
        }).collect(Collectors.toList());
        return top3List;
    }



}

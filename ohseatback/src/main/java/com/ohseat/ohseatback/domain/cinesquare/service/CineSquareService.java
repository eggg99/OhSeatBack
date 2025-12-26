package com.ohseat.ohseatback.domain.cinesquare.service;

import com.ohseat.ohseatback.domain.cinesquare.dto.CineSquareRequest;
import com.ohseat.ohseatback.domain.cinesquare.dto.CommentDTO;
import com.ohseat.ohseatback.domain.cinesquare.dto.LocationResponse;
import com.ohseat.ohseatback.domain.cinesquare.entity.CineSquare;
import com.ohseat.ohseatback.domain.cinesquare.dto.CineSquareResponse;
import com.ohseat.ohseatback.domain.cinesquare.entity.CommentDomain;
import com.ohseat.ohseatback.domain.cinesquare.mapper.CineSquareMapper;
import com.ohseat.ohseatback.domain.cinesquare.mapper.CineSquareRepository;
import com.ohseat.ohseatback.domain.common.policy.PostDeletePolicy;
import com.ohseat.ohseatback.domain.file.entity.FileEntity;
import com.ohseat.ohseatback.domain.file.service.FileService;
import com.ohseat.ohseatback.exception.business.PostNotFoundException;
import com.ohseat.ohseatback.exception.business.UnauthorizedException;
import com.ohseat.ohseatback.security.SecurityUtil;
import org.springframework.transaction.annotation.Transactional;
import com.ohseat.ohseatback.utils.LocationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CineSquareService {

    private final CineSquareRepository cineSquareRepository;
    private final CineSquareMapper cineSquareMapper;
    private final LocationUtils locationUtils;
    private final FileService fileService;
    private final PostDeletePolicy postDeletePolicy;

    // 게시글 등록
    public void createPost(CineSquare post) { cineSquareRepository.insertPost(post); }

    // 단건 조회
    @Transactional
    public CineSquareResponse getPost(Integer postId) {
        // 존재 여부 확인
        CineSquare post = cineSquareRepository.selectPostById(postId);
        if (post == null) return null;

        // 조회수 증가
        cineSquareRepository.increaseViewCount(postId);

        // 댓글, 좋아요 수
        int commentCount = cineSquareRepository.countCommentsByPostId(postId);
        int likeCount = cineSquareRepository.countLikesByPostId(postId);

        // 좋아요 여부
        Integer userId = SecurityUtil.getCurrentUserId();
        boolean liked = isLiked(postId);

        // Prev / Next
        Integer prevPostId = cineSquareRepository.selectPrevPostId(post.getCategoryId(), postId);
        Integer nextPostId = cineSquareRepository.selectNextPostId(post.getCategoryId(), postId);

        // 증가 반영된 데이터 재조회하여 반환
        CineSquare updatedPost =  cineSquareRepository.selectPostById(postId);

        // DTO 생성
        CineSquareResponse response = cineSquareMapper.toResponseDto(updatedPost);
        response.setCommentCount(commentCount);
        response.setLikeCount(likeCount);
        response.setPrevPostId(prevPostId);
        response.setNextPostId(nextPostId);
        response.setIsLiked(liked);

        return response;
    }

    // 카테고리별 게시글 무한 스크롤 조회
    public List<CineSquare> getPostsByScroll(Integer categoryId, Integer lastPostId, int limit, String orderType) {

        // orderType에 따라 정렬 컬럼 결정
        String orderBy = switch (orderType) {
            case "views" -> "cs.views DESC";
            case "comments" -> "commentCount DESC";
            case "likes" -> "cs.like_count DESC";
            default -> "cs.created_at DESC"; // default 최신순
        };

        return cineSquareRepository.selectPostsByScroll(categoryId, lastPostId, limit, orderBy);

    }

    // 전체 인기글 조회 (일주일 기준)
    public List<CineSquareResponse> getWeeklyRanking(int limit) {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(7);

        List<CineSquare> posts = cineSquareRepository.selectWeeklyRanking(startDate, endDate, limit);

        return posts.stream()
                .map(cineSquareMapper::toResponseDto)
                .peek(dto -> {
                    // 좋아요 수 넣기
                    dto.setLikeCount(cineSquareRepository.countLikesByPostId(dto.getPostId()));
                })
                .collect(Collectors.toList());
    }

    // 게시글 수정
    @Transactional
    public void updatePost(Integer postId, CineSquareRequest request,
                           List<Integer> newFileIds, List<Integer> deleteFileIds,
                           Integer representativeFileId) throws IOException {
        // 게시글 조회 및 권한 체크
        CineSquare existing = cineSquareRepository.selectPostById(postId);
        if (existing == null) throw new PostNotFoundException("게시글이 존재하지 않습니다.");
        if (!existing.getAuthorId().equals(SecurityUtil.getCurrentUserId()))
            throw new UnauthorizedException("게시글 수정 권한이 없습니다.");

        // 게시글 내용 업데이트
        existing.setCategoryId(request.getCategoryId());
        existing.setTitle(request.getTitle());
        existing.setContent(request.getContent());
        cineSquareRepository.updatePost(existing);

        // 파일 삭제
        if (deleteFileIds != null && !deleteFileIds.isEmpty()) {
            for (Integer fileId : deleteFileIds) {
                fileService.deleteFile(fileId);
            }
        }

        // 이미 업로드된 fileId 연결
        if (newFileIds != null && !newFileIds.isEmpty()) {
            fileService.attachFilesToEntity(newFileIds, "CINESQUARE_POST", postId);
        }

        // 대표 이미지 지정 (fileId 기반)
        if (representativeFileId != null) {
            fileService.setRepresentativeFile(representativeFileId,"CINESQUARE_POST", postId);
        }
        // representativeFileId가 null이면 기존 대표 이미지 유지
    }

    // 게시글 삭제
    @Transactional
    public void deletePostWithFiles(Integer postId) throws IOException {
        CineSquare post = cineSquareRepository.selectPostById(postId);
        if (post == null) throw new PostNotFoundException("게시글이 존재하지 않습니다.");

        Integer currentUserId = SecurityUtil.getCurrentUserId();

        postDeletePolicy.check(post.getAuthorId(), currentUserId, SecurityUtil.getCurrentUserRole());

        List<FileEntity> files = fileService.getFiles("CINESQUARE_POST", postId);
        for(FileEntity file : files) {
            fileService.deleteFile(file.getFileId());
        }

        cineSquareRepository.deletePost(postId);
    }

    public LocationResponse getLocation (double longitude, double latitude) {
        return locationUtils.getLocation(longitude, latitude);
    }

    public LocationResponse searchLocation (String searchValue) {
        return locationUtils.searchLocation(searchValue);
    }

    // 댓글 목록 조회
    public List<CommentDTO> getCommentList(Integer postId) {
        return cineSquareRepository.getCommentList(postId);
    }

    // 댓글 작성
    public void insertComment(Integer postId, String content) {
        Integer userId = SecurityUtil.getCurrentUserId();
        if (userId == null) {
            throw new IllegalStateException("로그인 필요");
        }
        cineSquareRepository.insertComment(userId, postId, content);
    }

    // 댓글 수정
    public void updateComment(Integer commentId, String content) {
        Integer userId = SecurityUtil.getCurrentUserId();
        if (userId == null)
            throw new IllegalStateException("로그인 필요");

        // 댓글 존재 확인
        CommentDomain comment = cineSquareRepository.getCommentById(commentId);
        if (comment == null)
            throw new PostNotFoundException("댓글이 존재하지 않습니다.");

        // 작성자 본인인지 검증
        if (!comment.getCommenterId().equals(userId))
            throw new UnauthorizedException("댓글 수정 권한이 없습니다.");

        // 수정 실행
        cineSquareRepository.updateComment(commentId, userId, content);
    }

    // 댓글 삭제
    public void deleteComment(Integer commentId) {
        cineSquareRepository.deleteComment(commentId);
    }

    // 좋아요 토글
    public boolean toggleLike(Integer postId) {
        Integer userId = SecurityUtil.getCurrentUserId();
        if (userId == null) {
            throw new IllegalStateException("로그인 필요");
        }

        int liked = cineSquareRepository.isPostLiked(postId, userId);
        if (liked == 0) {
            cineSquareRepository.insertPostLike(postId, userId);
            cineSquareRepository.increasePostLikeCount(postId);
            return true;
        } else {
            cineSquareRepository.deletePostLike(postId, userId);
            cineSquareRepository.decreasePostLikeCount(postId);
            return false;
        }
    }

    // 좋아요 개수
    public int likeCount(Integer postId) {
        return cineSquareRepository.countLikesByPostId(postId);
    }

    // 좋아요 여부
    public boolean isLiked(Integer postId) {
        Integer userId = SecurityUtil.getCurrentUserId();
        boolean liked = false;
        if (userId != null) {
            liked = cineSquareRepository.isPostLiked(postId, userId) > 0;
        }
        return liked;
    }

    // 댓글 개수
    public int commentCount(Integer postId) {
        return cineSquareRepository.countCommentsByPostId(postId);
    }
    public List<CineSquare> getRandomList() {
        return cineSquareRepository.selectRandomList();
    }

}

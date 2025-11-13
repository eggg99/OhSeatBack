package com.ohseat.ohseatback.domain.recommend.controller;

import com.ohseat.ohseatback.domain.recommend.entity.PostDomain;
import com.ohseat.ohseatback.domain.recommend.dto.CinemaDTO;
import com.ohseat.ohseatback.domain.recommend.dto.CommentDTO;
import com.ohseat.ohseatback.domain.recommend.dto.PostDTO;
import com.ohseat.ohseatback.domain.recommend.dto.ScreenDTO;
import com.ohseat.ohseatback.domain.recommend.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rcmd")
public class RecommendContoller {

    @Autowired
    private RecommendService recommendService;

    @GetMapping("/trendingCinema")
    public ResponseEntity<List<CinemaDTO>> getTrendingCinema() {
        List<CinemaDTO> trendingCinema = recommendService.getTrendingCinema();
        return ResponseEntity.ok(trendingCinema);
    }

    // 영화관 리스트 조회
    @GetMapping("/cinemaList")
    public ResponseEntity<?> getCinemaList(
            @RequestParam(required = false) Integer multiplexId,
            @RequestParam(required = false) Integer areaId) {

        if (multiplexId == null) {
            return ResponseEntity.badRequest().body("멀티플렉스 값이 비었습니다.");
        }

        List<CinemaDTO> cinemaList = recommendService.getCinemaList(multiplexId, areaId);
        return ResponseEntity.ok(cinemaList);
    }

    // 상영관 리스트 조회
    @GetMapping("/screenList")
    public ResponseEntity<List<ScreenDTO>> getScreenList(@RequestParam Integer multiplexId, @RequestParam String cinemaId) {
        List<ScreenDTO> screen = recommendService.getScreenList(multiplexId, cinemaId);
        return ResponseEntity.ok(screen);
    }

    // 게시글 리스트 조회 (페이징)
    @GetMapping("/postList")
    public ResponseEntity<Page<PostDTO>> getPostList(
            @RequestParam Integer multiplexId,
            @RequestParam String areaId,
            @RequestParam String cinemaId,
            @RequestParam String screenId,
            @RequestParam(defaultValue = "latest") String orderType,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<PostDTO> postList = recommendService.getPostList(multiplexId, areaId, cinemaId, screenId, orderType, page, size);
        return ResponseEntity.ok(postList);
    }

    // 게시글 상세 조회
    @GetMapping("/postDetail/{postId}")
    public ResponseEntity<PostDTO> getPostDetail(@PathVariable Integer postId) {
        PostDTO postDomain = recommendService.getPostDetail(postId);
        return ResponseEntity.ok(postDomain);
    }

    // 댓글 리스트 조회
    @GetMapping("/commentList")
    public ResponseEntity<List<CommentDTO>> getCommentList(@RequestParam Integer postId) {
        List<CommentDTO> comment = recommendService.getCommentList(postId);
        return ResponseEntity.ok(comment);
    }

    // 댓글 작성
    @PostMapping("/comment")
    public ResponseEntity<String> putComment(@RequestBody CommentDTO dto) {
        recommendService.putComment(dto.getCommenterId(), dto.getPostId(), dto.getContent());
        return ResponseEntity.ok("댓글 추가 완료");
    }

    // 댓글 수정
    @PutMapping("/comment/edit/{commentId}")
    public ResponseEntity<String> editComment(@RequestBody CommentDTO dto, @PathVariable Integer commentId) {
        recommendService.updateComment(dto.getContent(), commentId);
        return ResponseEntity.ok("댓글 수정 완료");
    }

    // 게시글 작성
    @PostMapping("/post/reg")
    public ResponseEntity<Integer> putPost(@RequestBody PostDomain domain) {
        Integer postId = recommendService.putPost(
                domain.getAuthorId(),
                domain.getMultiplexId(),
                domain.getAreaId(),
                domain.getCinemaId(),
                domain.getScreenId(),
                domain.getTitle(),
                domain.getContent()
        );
        return ResponseEntity.ok(postId);
    }

    // 게시글 변경
    @PostMapping("/post/edit/{postId}")
    public ResponseEntity<String> updatePost(@RequestBody PostDomain domain, @PathVariable Integer postId) {
        recommendService.updatePost(
                domain.getMultiplexId(),
                domain.getAreaId(),
                domain.getCinemaId(),
                domain.getScreenId(),
                domain.getTitle(),
                domain.getContent(),
                postId
        );
        return ResponseEntity.ok("포스트 수정 완료");
    }

    // 게시글 삭제
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Integer postId) {
        recommendService.deletePost(postId);
        return ResponseEntity.ok("포스트 삭제 완료");
    }

    // 댓글 삭제
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Integer commentId) {
        recommendService.deleteComment(commentId);
        return ResponseEntity.ok("댓글 삭제 완료");
    }

    // 조회수 증가
    @PostMapping("/incrementViews/{postId}")
    public ResponseEntity<String>  increaseViewCount(@PathVariable Integer postId) {
        recommendService.incrementViewCount(postId);
        return ResponseEntity.ok("조회수 증가 완료");
    }

    @PostMapping("/post/like/{postId}")
    public ResponseEntity<Map<String, Boolean>> likePost(@PathVariable Integer postId) {
        Map<String, Boolean> result= recommendService.updatePostLike(postId);
        return ResponseEntity.ok(result);
    }

    // 게시글 최신 3개 조회 - 메인
    @GetMapping("/post/top3List")
    public ResponseEntity<List<PostDTO>> getPostList() {
        List<PostDTO> postList = recommendService.getPostListRecentTop3();
        return ResponseEntity.ok(postList);
    }
}

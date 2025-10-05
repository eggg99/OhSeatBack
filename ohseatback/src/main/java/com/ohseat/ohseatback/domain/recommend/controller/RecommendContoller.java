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

@RestController
@RequestMapping("/api/rcmd")
public class RecommendContoller {

    @Autowired
    private RecommendService recommendService;

    // 영화관 리스트 조회
    @GetMapping("/cinemaList")
    public ResponseEntity<List<CinemaDTO>> getCinemaList(@RequestParam Integer multiplexId, @RequestParam Integer areaId) {
        List<CinemaDTO> cinema = recommendService.getCinemaList(multiplexId, areaId);
        return ResponseEntity.ok(cinema);
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
            @RequestParam Integer areaId,
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
    @PutMapping("/comment")
    public ResponseEntity<String> putComment(@RequestBody CommentDTO dto) {
        recommendService.putComment(dto.getCommenterId(), dto.getPostId(), dto.getContent());
        return ResponseEntity.ok("댓글 추가 완료");
    }

    // 게시글 작성
    @PutMapping("/post/reg")
    public ResponseEntity<String> putPost(@RequestBody PostDomain domain) {
        recommendService.putPost(
                domain.getAuthorId(),
                domain.getMultiplexId(),
                domain.getAreaId(),
                domain.getCinemaId(),
                domain.getScreenId(),
                domain.getTitle(),
                domain.getContent()
        );
        return ResponseEntity.ok("포스트 등록 완료");
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



}

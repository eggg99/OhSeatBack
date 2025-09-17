package com.ohseat.ohseatback.controller;

import com.ohseat.ohseatback.domain.Cinema;
import com.ohseat.ohseatback.dto.recommend.CinemaDTO;
import com.ohseat.ohseatback.dto.recommend.CommentDTO;
import com.ohseat.ohseatback.dto.recommend.PostDTO;
import com.ohseat.ohseatback.dto.recommend.ScreenDTO;
import com.ohseat.ohseatback.service.RecommendService;
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

    @GetMapping("/postDetail/{postId}")
    public ResponseEntity<PostDTO> getPostDetail(@PathVariable Integer postId) {
        PostDTO postDomain = recommendService.getPostDetail(postId);
        return ResponseEntity.ok(postDomain);
    }

    @GetMapping("/commentList")
    public ResponseEntity<List<CommentDTO>> getCommentList(@RequestParam Integer postId) {
        List<CommentDTO> comment = recommendService.getCommentList(postId);
        return ResponseEntity.ok(comment);
    }

    @PutMapping("/comment")
    public ResponseEntity<String> putComment(@RequestBody CommentDTO dto) {
        recommendService.putComment(dto.getCommenterId(), dto.getPostId(), dto.getContent());
        return ResponseEntity.ok("댓글 추가 완료");
    }



}

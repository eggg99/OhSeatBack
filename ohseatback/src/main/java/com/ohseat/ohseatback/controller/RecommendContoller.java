package com.ohseat.ohseatback.controller;

import com.ohseat.ohseatback.domain.Cinema;
import com.ohseat.ohseatback.domain.PostDomain;
import com.ohseat.ohseatback.dto.recommend.CommentDTO;
import com.ohseat.ohseatback.dto.recommend.PostListDTO;
import com.ohseat.ohseatback.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rcmd")
public class RecommendContoller {

    @Autowired
    private RecommendService recommendService;

    // 영화관 리스트 조회
    @GetMapping("/cinemaList")
    public ResponseEntity<List<Cinema>> getCinemaList(@RequestParam Integer multiplexId, @RequestParam Integer areaId) {
        List<Cinema> cinema = recommendService.getCinemaList(multiplexId, areaId);
        return ResponseEntity.ok(cinema);
    }

    // 상영관 리스트 조회
    @GetMapping("/screenList")
    public ResponseEntity<List<Cinema>> getScreenList(@RequestParam Integer multiplexId, @RequestParam String cinemaId) {
        List<Cinema> cinema = recommendService.getScreenList(multiplexId, cinemaId);
        return ResponseEntity.ok(cinema);
    }

    // 게시글 리스트 조회 (페이징)
    @GetMapping("/postList")
    public ResponseEntity<Page<PostListDTO>> getPostList(
            @RequestParam String cinemaId,
            @RequestParam String screenId,
            @RequestParam(defaultValue = "latest") String orderType,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<PostListDTO> postList = recommendService.getPostList(cinemaId, screenId, orderType, page, size);
        return ResponseEntity.ok(postList);
    }



}

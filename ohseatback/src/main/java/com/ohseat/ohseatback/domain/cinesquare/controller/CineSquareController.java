package com.ohseat.ohseatback.domain.cinesquare.controller;


import com.ohseat.ohseatback.domain.cinesquare.dto.LocationResponse;
import com.ohseat.ohseatback.domain.cinesquare.entity.CineSquare;
import com.ohseat.ohseatback.domain.cinesquare.dto.CineSquareRequest;
import com.ohseat.ohseatback.domain.cinesquare.dto.CineSquareResponse;
import com.ohseat.ohseatback.exception.business.PostNotFoundException;
import com.ohseat.ohseatback.exception.business.UnauthorizedException;
import com.ohseat.ohseatback.domain.cinesquare.mapper.CineSquareMapper;
import com.ohseat.ohseatback.security.SecurityUtil;
import com.ohseat.ohseatback.domain.cinesquare.service.CineSquareService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cinesquare")
@RequiredArgsConstructor
public class CineSquareController {

    private final CineSquareService cineSquareService;
    private final CineSquareMapper cineSquareMapper;

    // 카테고리별 전체 글 조회
    @GetMapping("/list")
    public ResponseEntity<List<CineSquareResponse>> getAllPosts(
            @RequestParam Integer categoryId,
            @RequestParam(required = false) Integer lastPostId,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "latest") String orderType
    ) {
        List<CineSquare> posts = cineSquareService.getPostsByScroll(categoryId, lastPostId, limit, orderType);

        if (posts.isEmpty()) {
            throw new PostNotFoundException("더 이상 불러올 게시글이 없습니다.");
        }

        List<CineSquareResponse> dtoList = posts.stream()
                .map(cineSquareMapper::toResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    // 단건 조회
    @GetMapping("/{postId}")
    public ResponseEntity<CineSquareResponse> getPost(@PathVariable Integer postId) {
        CineSquare post = cineSquareService.getPost(postId);
        if (post == null) {
            throw new PostNotFoundException("게시글이 존재하지 않습니다.");
        }
        return ResponseEntity.ok(cineSquareMapper.toResponseDto(post));
    }

    // 게시글 작성
    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody CineSquareRequest request) {
        CineSquare post = new CineSquare();
        post.setCategoryId(request.getCategoryId());
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setAuthorId(SecurityUtil.getCurrentUserId());
        cineSquareService.createPost(post);

        return ResponseEntity.ok("포스트 등록 완료");
    }

    // 게시글 수정
    @PutMapping("/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable Integer postId,
                                             @RequestBody CineSquareRequest request) {
        CineSquare existingPost = cineSquareService.getPost(postId);
        if (existingPost == null) {
            throw new PostNotFoundException("게시글이 존재하지 않습니다.");
        }

        if (!existingPost.getAuthorId().equals(SecurityUtil.getCurrentUserId())) {
            throw new UnauthorizedException("게시글 수정 권한이 없습니다.");
        }

        existingPost.setCategoryId(request.getCategoryId());
        existingPost.setTitle(request.getTitle());
        existingPost.setContent(request.getContent());

        cineSquareService.updatePost(existingPost);

        return ResponseEntity.ok("포스트 수정 완료");
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Integer postId) {
        CineSquare existingPost = cineSquareService.getPost(postId);
        if (existingPost == null) {
            throw new PostNotFoundException("게시글이 존재하지 않습니다.");
        }

        if (!existingPost.getAuthorId().equals(SecurityUtil.getCurrentUserId())) {
            throw new UnauthorizedException("게시글 삭제 권한이 없습니다.");
        }

        cineSquareService.deletePost(postId, SecurityUtil.getCurrentUserId());

        return ResponseEntity.ok("포스트 삭제 완료");
    }

    @GetMapping("/location")
    public ResponseEntity<LocationResponse> getLocation(@RequestParam("x") Double longitude,
                                                        @RequestParam("y") Double latitude) {
        LocationResponse location = cineSquareService.getLocation(longitude, latitude);
        return ResponseEntity.ok(location);
    }
}

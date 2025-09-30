package com.ohseat.ohseatback.controller;


import com.ohseat.ohseatback.domain.CineSquare;
import com.ohseat.ohseatback.dto.CineSquareRequest;
import com.ohseat.ohseatback.dto.CineSquareResponse;
import com.ohseat.ohseatback.exception.business.PostNotFoundException;
import com.ohseat.ohseatback.exception.business.UnauthorizedException;
import com.ohseat.ohseatback.mapper.CineSquareMapper;
import com.ohseat.ohseatback.security.SecurityUtil;
import com.ohseat.ohseatback.service.CineSquareService;
import com.ohseat.ohseatback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<Page<CineSquareResponse>> getAllPosts(
            @RequestParam Integer categoryId,
            @RequestParam(defaultValue = "latest") String orderType,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<CineSquareResponse> posts = cineSquareService.getAllPosts(categoryId, orderType, page, size);
//        List<CineSquare> posts = cineSquareService.getAllPosts(categoryId);

        if (posts.isEmpty()) {
            throw new PostNotFoundException("해당 카테고리의 게시글이 없습니다.");
        }

        return ResponseEntity.ok(posts);

//        return posts.stream()
//                .map(this::toResponseDto)
//                .collect(Collectors.toList());
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

}

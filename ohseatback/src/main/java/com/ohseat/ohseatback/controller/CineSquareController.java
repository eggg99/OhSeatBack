package com.ohseat.ohseatback.controller;


import com.ohseat.ohseatback.domain.CineSquare;
import com.ohseat.ohseatback.dto.CineSquareRequest;
import com.ohseat.ohseatback.dto.CineSquareResponse;
import com.ohseat.ohseatback.exception.business.PostNotFoundException;
import com.ohseat.ohseatback.exception.business.UnauthorizedException;
import com.ohseat.ohseatback.security.SecurityUtil;
import com.ohseat.ohseatback.service.CineSquareService;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cinesquare")
@RequiredArgsConstructor
public class CineSquareController {

    private final CineSquareService cineSquareService;

    // 카테고리별 전체 글 조회
    @GetMapping("/list")
    public List<CineSquareResponse> getAllPosts(@RequestParam String category) {
        List<CineSquare> posts = cineSquareService.getAllPosts(category);

        if (posts.isEmpty()) {
            throw new PostNotFoundException("해당 카테고리의 게시글이 없습니다.");
        }

        return posts.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    // 단건 조회
    @GetMapping("/{postId}")
    public CineSquareResponse getPost(@PathVariable Integer postId) {
        CineSquare post = cineSquareService.getPost(postId);
        if (post == null) {
            throw new PostNotFoundException("게시글이 존재하지 않습니다.");
        }
        return toResponseDto(post);
    }

    // 글 작성
    @PostMapping
    public void createPost(@RequestBody CineSquareRequest request) {
        CineSquare post = new CineSquare();
        post.setCategory(request.getCategory());
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setAuthorId(SecurityUtil.getCurrentUserId());
        cineSquareService.createPost(post);
    }

    // 글 수정
    @PutMapping("/{postId}")
    public void updatePost(@PathVariable Integer postId,
                           @RequestBody CineSquareRequest request) {
        CineSquare existingPost = cineSquareService.getPost(postId);
        if (existingPost == null) {
            throw new PostNotFoundException("게시글이 존재하지 않습니다.");
        }

        if (!existingPost.getAuthorId().equals(SecurityUtil.getCurrentUserId())) {
            throw new UnauthorizedException("게시글 수정 권한이 없습니다.");
        }

        existingPost.setCategory(request.getCategory());
        existingPost.setTitle(request.getTitle());
        existingPost.setContent(request.getContent());

        cineSquareService.updatePost(existingPost);
    }

    // 글 삭제
    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Integer postId) {
        CineSquare existingPost = cineSquareService.getPost(postId);
        if (existingPost == null) {
            throw new PostNotFoundException("게시글이 존재하지 않습니다.");
        }

        if (!existingPost.getAuthorId().equals(SecurityUtil.getCurrentUserId())) {
            throw new UnauthorizedException("게시글 삭제 권한이 없습니다.");
        }

        cineSquareService.deletePost(postId, SecurityUtil.getCurrentUserId());
    }

    private CineSquareResponse toResponseDto(CineSquare post) {
        CineSquareResponse dto = new CineSquareResponse();
        dto.setPostId(post.getPostId());
        dto.setCategory(post.getCategory());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setViews(post.getViews());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setAuthorId(post.getAuthorId());
        return dto;
    }

}

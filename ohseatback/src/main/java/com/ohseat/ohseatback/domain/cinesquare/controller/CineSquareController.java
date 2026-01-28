package com.ohseat.ohseatback.domain.cinesquare.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohseat.ohseatback.domain.cinesquare.dto.CommentDTO;
import com.ohseat.ohseatback.domain.cinesquare.dto.LocationResponse;
import com.ohseat.ohseatback.domain.cinesquare.entity.CineSquare;
import com.ohseat.ohseatback.domain.cinesquare.dto.CineSquareRequest;
import com.ohseat.ohseatback.domain.cinesquare.dto.CineSquareResponse;
import com.ohseat.ohseatback.domain.cinesquare.service.LocationService;
import com.ohseat.ohseatback.domain.file.dto.FileResponse;
import com.ohseat.ohseatback.domain.file.service.FileService;
import com.ohseat.ohseatback.exception.business.PostNotFoundException;
import com.ohseat.ohseatback.exception.business.UnauthorizedException;
import com.ohseat.ohseatback.domain.cinesquare.mapper.CineSquareMapper;
import com.ohseat.ohseatback.security.SecurityUtil;
import com.ohseat.ohseatback.domain.cinesquare.service.CineSquareService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cinesquare")
@RequiredArgsConstructor
public class CineSquareController {

    private final CineSquareService cineSquareService;
    private final CineSquareMapper cineSquareMapper;
    private final LocationService locationService;
    private final FileService fileService;

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
                .map(post -> {
                    CineSquareResponse dto = cineSquareMapper.toResponseDto(post);

                    // 파일 정보 조회
                    List<FileResponse> files = fileService.getFiles("CINESQUARE_POST", post.getPostId())
                            .stream()
                            .map(FileResponse::from)
                            .collect(Collectors.toList());

                    // 대표 이미지 + 갯수만 세팅
                    FileResponse representativeFile = files.stream()
                            .filter(f -> "Y".equalsIgnoreCase(f.getIsRepresentative()))
                            .findFirst()
                            .orElse(null);

                    dto.setRepresentativeFile(representativeFile);
                    dto.setTotalFiles(files.size());
                    dto.setFiles(null); // 목록에서는 전체 파일 리스트 비워둠 (응답 최소화)

                    // 댓글 개수
                    dto.setCommentCount(cineSquareService.commentCount(post.getPostId()));

                    // 좋아요 여부
                    dto.setIsLiked(cineSquareService.isLiked(post.getPostId()));
                    // 좋아요 개수
                    dto.setLikeCount(cineSquareService.likeCount(post.getPostId()));

                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    // 전체 인기글 조회 (일주일 기준)
    @GetMapping("/ranking/week")
    public ResponseEntity<List<CineSquareResponse>> getWeeklyRanking(
            @RequestParam(defaultValue = "10") int limit
    ) {
        return ResponseEntity.ok(cineSquareService.getWeeklyRanking(limit));
    }

    // 단건 조회
    @GetMapping("/{postId}")
    public ResponseEntity<CineSquareResponse> getPost(@PathVariable Integer postId, HttpServletRequest request, HttpServletResponse response) {
        CineSquareResponse dto = cineSquareService.getPost(postId, request, response);

        if (response == null) {
            throw new PostNotFoundException("게시글이 존재하지 않습니다.");
        }

        dto.setFiles(fileService.getFiles("CINESQUARE_POST", postId)
                .stream()
                .map(FileResponse::from)
                .collect(Collectors.toList())
        );

        return ResponseEntity.ok(dto);
    }

    // 게시글 작성
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createPost(
            @RequestParam("data") String dataJson,
            @RequestParam(value = "newFileIds", required = false) List<Integer> newFileIds,
            @RequestParam(value = "representativeFileId", required = false) Integer representativeFileId
    ) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CineSquareRequest request = mapper.readValue(dataJson, CineSquareRequest.class);

        CineSquare post = new CineSquare();
        post.setCategoryId(request.getCategoryId());
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setAuthorId(SecurityUtil.getCurrentUserId());
        post.setCity(request.getCity());
        post.setDistrict(request.getDistrict());

        cineSquareService.createPost(post);

        if (newFileIds != null && !newFileIds.isEmpty()) {
            fileService.attachFilesToEntity(newFileIds, "CINESQUARE_POST", post.getPostId());
        }
        if (representativeFileId != null) {
            fileService.setRepresentativeFile(representativeFileId, "CINESQUARE_POST", post.getPostId());
        }

        return ResponseEntity.ok("게시글 등록 완료");
    }

    // 게시글 수정
    @PutMapping("/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable Integer postId,
                                             @RequestParam("data") String dataJson,
                                             @RequestParam(value = "newFileIds", required = false) List<Integer> newFileIds,
                                             @RequestParam(value = "deleteFileIds", required = false) List<Integer> deleteFileIds,
                                             @RequestParam(value = "representativeFileId", required = false) Integer representativeFileId
    ) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CineSquareRequest request = mapper.readValue(dataJson, CineSquareRequest.class);

        cineSquareService.updatePost(postId, request, newFileIds, deleteFileIds, representativeFileId);
        return ResponseEntity.ok("게시글 수정 완료");
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Integer postId) throws IOException {
        cineSquareService.deletePostWithFiles(postId);
        return ResponseEntity.ok("게시글 삭제 완료");
    }

    @GetMapping("/location")
    public ResponseEntity<LocationResponse> getLocation(@RequestParam("x") Double longitude,
                                                        @RequestParam("y") Double latitude) {
        LocationResponse location = cineSquareService.getLocation(longitude, latitude);
        return ResponseEntity.ok(location);
    }

    @GetMapping("/searchLocation")
    public ResponseEntity<List<LocationResponse>> searchLocation(@RequestParam("searchValue") String searchValue) {
        List<LocationResponse> location = locationService.search(searchValue);
        return ResponseEntity.ok(location);
    }

    // 댓글 목록 조회
    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable Integer postId) {
        return ResponseEntity.ok(cineSquareService.getCommentList(postId));
    }

    // 댓글 작성
    @PostMapping("/{postId}/comments")
    public ResponseEntity<String> addComment(@PathVariable Integer postId, @RequestBody Map<String, String> body) {
        cineSquareService.insertComment(postId, body.get("content"));
        return ResponseEntity.ok("댓글 작성 완료");
    }

    // 댓글 수정
    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable Integer commentId, @RequestBody Map<String, String> body) {
        String content = body.get("content");
        cineSquareService.updateComment(commentId, content);
        return ResponseEntity.ok("댓글 수정 완료");
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Integer commentId) {
        cineSquareService.deleteComment(commentId);
        return ResponseEntity.ok("댓글 삭제 완료");
    }

    // 좋아요 토글
    @PostMapping("/{postId}/like")
    public ResponseEntity<Map<String, Boolean>> toggleLike(@PathVariable Integer postId) {
        boolean liked = cineSquareService.toggleLike(postId);
        return ResponseEntity.ok(Map.of("liked", liked));
    }

    @GetMapping("/main/random")
    public ResponseEntity<List<CineSquare>> random() {
        return ResponseEntity.ok(cineSquareService.getRandomList());
    }
}

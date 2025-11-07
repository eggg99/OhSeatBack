package com.ohseat.ohseatback.domain.cinesquare.controller;


import com.ohseat.ohseatback.domain.cinesquare.dto.LocationResponse;
import com.ohseat.ohseatback.domain.cinesquare.entity.CineSquare;
import com.ohseat.ohseatback.domain.cinesquare.dto.CineSquareRequest;
import com.ohseat.ohseatback.domain.cinesquare.dto.CineSquareResponse;
import com.ohseat.ohseatback.domain.cinesquare.service.LocationService;
import com.ohseat.ohseatback.domain.file.dto.FileResponse;
import com.ohseat.ohseatback.domain.file.entity.FileEntity;
import com.ohseat.ohseatback.domain.file.service.FileService;
import com.ohseat.ohseatback.exception.business.PostNotFoundException;
import com.ohseat.ohseatback.exception.business.UnauthorizedException;
import com.ohseat.ohseatback.domain.cinesquare.mapper.CineSquareMapper;
import com.ohseat.ohseatback.security.SecurityUtil;
import com.ohseat.ohseatback.domain.cinesquare.service.CineSquareService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
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

                    return dto;
                })
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

        CineSquareResponse response = cineSquareMapper.toResponseDto(post);
        response.setFiles(fileService.getFiles("CINESQUARE_POST", postId)
                .stream()
                .map(FileResponse::from)
                .collect(Collectors.toList())
        );

        return ResponseEntity.ok(response);
    }

    // 게시글 작성
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createPost(@RequestPart("data") CineSquareRequest request,
                                             @RequestPart(value = "files", required = false) List<MultipartFile> files,
                                             @RequestPart(value = "representatives", required = false) List<String> representatives
    ) throws IOException {
        // 1. 게시글 저장
        CineSquare post = new CineSquare();
        post.setCategoryId(request.getCategoryId());
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setAuthorId(SecurityUtil.getCurrentUserId());
        post.setCity(request.getCity());
        post.setDistrict(request.getDistrict());

        cineSquareService.createPost(post);

        // 2. 파일 저장 (있을 때만)
        if (files != null && !files.isEmpty()) {
            // representatives가 null이면 기존 규칙으로 동작
            fileService.saveFiles(files, representatives,"CINESQUARE_POST", post.getPostId());
        }

        return ResponseEntity.ok("게시글 등록 완료");
    }

    // 게시글 수정
    @PutMapping(value = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updatePost(@PathVariable Integer postId,
                                             @RequestPart("data") CineSquareRequest request,
                                             @RequestPart(value = "new_files", required = false) List<MultipartFile> newFiles,
                                             @RequestPart(value = "delete_files_ids", required = false) List<Integer> deleteFileIds
    ) throws IOException {
        cineSquareService.updatePost(postId, request, newFiles, deleteFileIds);
        return ResponseEntity.ok("게시글 수정 완료");
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Integer postId) throws IOException {
        cineSquareService.deletePostWithFiles(postId, SecurityUtil.getCurrentUserId());
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
}

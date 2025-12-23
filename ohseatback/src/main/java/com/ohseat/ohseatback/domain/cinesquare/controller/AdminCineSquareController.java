package com.ohseat.ohseatback.domain.cinesquare.controller;

import com.ohseat.ohseatback.domain.notice.service.AdminCineSquareService;
import com.ohseat.ohseatback.domain.cinesquare.dto.CineSquareRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/cinesquare")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminCineSquareController {
/*
    private final AdminCineSquareService adminCineSquareService;

//    // 관리자 - 공지 글 작성
//    @PostMapping("/notice")
//    public ResponseEntity<String> notice(@RequestBody CineSquareRequest request) {
//        adminCineSquareService.createNotice(request);
//        return ResponseEntity.ok("씨네광장 공지 등록 완료");
//    }

    // 관리자 - 글 삭제
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<String> delete(@PathVariable Integer postId) {
        adminCineSquareService.deletePost(postId);
        return ResponseEntity.ok("관리자에 의해 게시글 삭제됨");
    }

 */
}

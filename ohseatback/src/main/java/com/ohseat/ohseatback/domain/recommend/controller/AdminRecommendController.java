package com.ohseat.ohseatback.domain.recommend.controller;


import com.ohseat.ohseatback.domain.notice.service.AdminRecommendService;
import com.ohseat.ohseatback.domain.recommend.entity.PostDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/recommend")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminRecommendController {

    private final AdminRecommendService adminRecommendService;

    @PostMapping("/notice")
    public ResponseEntity<Integer> createNotice(@RequestBody PostDomain domain) {
        Integer id = adminRecommendService.createNotice(domain);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<String> delete(@PathVariable Integer postId) {
        adminRecommendService.deletePost(postId);
        return ResponseEntity.ok("관리자에 의해 게시글 삭제됨");
    }

}

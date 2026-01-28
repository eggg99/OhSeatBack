package com.ohseat.ohseatback.domain.common.controller;

import com.ohseat.ohseatback.domain.common.dto.AdminPostBulkDeleteRequest;
import com.ohseat.ohseatback.domain.common.service.AdminPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/posts")
@PreAuthorize("hasRole('ADMIN')")
public class AdminPostController {

    private final AdminPostService adminPostService;

    // 관리자 게시글 다건 삭제
    @DeleteMapping("/bulk")
    public ResponseEntity<Void> bulkDelete(@RequestBody AdminPostBulkDeleteRequest request) {
        adminPostService.bulkDelete(request.getBoardType(), request.getPostIds());
        return ResponseEntity.noContent().build();
    }

}

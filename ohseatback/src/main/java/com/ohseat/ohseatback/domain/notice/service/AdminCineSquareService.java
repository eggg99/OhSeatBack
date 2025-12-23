package com.ohseat.ohseatback.domain.notice.service;


import com.ohseat.ohseatback.domain.cinesquare.dto.CineSquareRequest;
import com.ohseat.ohseatback.domain.cinesquare.entity.CineSquare;
import com.ohseat.ohseatback.domain.cinesquare.mapper.CineSquareRepository;
import com.ohseat.ohseatback.domain.file.entity.FileEntity;
import com.ohseat.ohseatback.domain.file.service.FileService;
import com.ohseat.ohseatback.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 삭제 예정
@Service
@RequiredArgsConstructor
public class AdminCineSquareService {
/*
    private final CineSquareRepository cineSquareRepository;
    private final FileService fileService;

    // 관리자 공지 등록
    @Transactional
    public void createNotice(CineSquareRequest request) {
        Integer userId = SecurityUtil.getCurrentUserId();

        CineSquare post = new CineSquare();
        post.setCategoryId(request.getCategoryId());
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setAuthorId(userId); // 관리자 작성으로 처리
        post.setCity(request.getCity());
        post.setDistrict(request.getDistrict());
//        post.setIsNotice(true); // 관리자 공지로 설정

//        cineSquareRepository.insertNoticePost(post);
    }

    // 관리자 글 삭제
    @Transactional
    public void deletePost(Integer postId) {
        // 파일 전체 삭제
        List<FileEntity> files = fileService.getFiles("CINESQUARE_POST", postId);
        for (FileEntity f : files) fileService.deleteFile(f.getFileId());
        cineSquareRepository.deletePostByAdmin(postId);
    }
*/
}

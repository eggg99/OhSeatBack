package com.ohseat.ohseatback.domain.cinesquare.service;

import com.ohseat.ohseatback.domain.cinesquare.dto.CineSquareRequest;
import com.ohseat.ohseatback.domain.cinesquare.dto.LocationResponse;
import com.ohseat.ohseatback.domain.cinesquare.entity.CineSquare;
import com.ohseat.ohseatback.domain.cinesquare.dto.CineSquareResponse;
import com.ohseat.ohseatback.domain.cinesquare.mapper.CineSquareMapper;
import com.ohseat.ohseatback.domain.cinesquare.mapper.CineSquareRepository;
import com.ohseat.ohseatback.domain.file.entity.FileEntity;
import com.ohseat.ohseatback.domain.file.service.FileService;
import com.ohseat.ohseatback.exception.business.PostNotFoundException;
import com.ohseat.ohseatback.exception.business.UnauthorizedException;
import com.ohseat.ohseatback.security.SecurityUtil;
import jakarta.transaction.Transactional;
import com.ohseat.ohseatback.utils.LocationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CineSquareService {

    private final CineSquareRepository cineSquareRepository;
    private final LocationUtils locationUtils;
    private final FileService fileService;

    // 게시글 등록
    public void createPost(CineSquare post) { cineSquareRepository.insertPost(post); }

    // 단건 조회
    @Transactional
    public CineSquare getPost(Integer postId) {
        // 1) 존재 여부 확인
        CineSquare post = cineSquareRepository.selectPostById(postId);
        if (post == null) return null;

        // 2) 조회수 증가
        cineSquareRepository.increaseViewCount(postId);

        // 3) 증가 반영된 데이터 재조회하여 반환
        return cineSquareRepository.selectPostById(postId);
    }

    // 카테고리별 게시글 무한 스크롤 조회
    public List<CineSquare> getPostsByScroll(Integer categoryId, Integer lastPostId, int limit, String orderType) {

        // orderType에 따라 정렬 컬럼 결정
        String orderBy = switch (orderType) {
            case "views" -> "cs.views DESC";
            case "comments" -> "cs.comments DESC";
            default -> "cs.created_at DESC"; // default 최신순
        };

        return cineSquareRepository.selectPostsByScroll(categoryId, lastPostId, limit, orderBy);

    }

    // 게시글 수정
    @Transactional
    public void updatePost(Integer postId, CineSquareRequest request,
                           List<MultipartFile> newFiles, List<Integer> deleteFileIds) throws IOException {
        CineSquare existing = cineSquareRepository.selectPostById(postId);
        if (existing == null) throw new PostNotFoundException("게시글이 존재하지 않습니다.");
        if (!existing.getAuthorId().equals(SecurityUtil.getCurrentUserId()))
            throw new UnauthorizedException("게시글 수정 권한이 없습니다.");

        existing.setCategoryId(request.getCategoryId());
        existing.setTitle(request.getTitle());
        existing.setContent(request.getContent());
        cineSquareRepository.updatePost(existing);

        if (deleteFileIds != null) {
            for (Integer fileId : deleteFileIds) {
                fileService.deleteFile(fileId);
            }
        }

        if (newFiles != null && !newFiles.isEmpty()) {
            fileService.saveFiles(newFiles, "CINESQUARE_POST", postId);
        }
    }

    // 게시글 삭제
    @Transactional
    public void deletePostWithFiles(Integer postId, Integer authorId) throws IOException {
        CineSquare post = cineSquareRepository.selectPostById(postId);
        if (post == null) throw new PostNotFoundException("게시글이 존재하지 않습니다.");
        if (!post.getAuthorId().equals(authorId))
            throw new UnauthorizedException("게시글 삭제 권한이 없습니다.");

        List<FileEntity> files = fileService.getFiles("CINESQUARE_POST", postId);
        for(FileEntity file : files) {
            fileService.deleteFile(file.getFileId());
        }

        cineSquareRepository.deletePost(postId, authorId);
    }

    public LocationResponse getLocation (double longitude, double latitude) {
        return locationUtils.getLocation(longitude, latitude);
    }

    public LocationResponse searchLocation (String searchValue) {
        return locationUtils.searchLocation(searchValue);
    }

}

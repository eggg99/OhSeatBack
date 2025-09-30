package com.ohseat.ohseatback.mapper;

import com.ohseat.ohseatback.domain.CineSquare;
import com.ohseat.ohseatback.dto.CineSquareResponse;
import com.ohseat.ohseatback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

//DTO 변환용 Mapper
@Component
@RequiredArgsConstructor
public class CineSquareMapper {

    private final UserService userService;

    public CineSquareResponse toResponseDto(CineSquare post) {
        return CineSquareResponse.builder()
                .postId(post.getPostId())
                .categoryId(post.getCategoryId())
                .categoryName(post.getCategoryName())
                .title(post.getTitle())
                .content(post.getContent())
                .views(post.getViews())
                .createdAt(post.getCreatedAt())
                .authorId(post.getAuthorId())
                .authorNickname(userService.getUserById(post.getAuthorId()).getNickname())
                .build();
    }

}


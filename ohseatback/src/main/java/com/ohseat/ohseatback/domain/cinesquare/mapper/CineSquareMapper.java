package com.ohseat.ohseatback.domain.cinesquare.mapper;

import com.ohseat.ohseatback.domain.cinesquare.entity.CineSquare;
import com.ohseat.ohseatback.domain.cinesquare.dto.CineSquareResponse;
import com.ohseat.ohseatback.domain.user.service.UserService;
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
                .city(post.getCity())
                .district(post.getDistrict())
                .build();
    }

}


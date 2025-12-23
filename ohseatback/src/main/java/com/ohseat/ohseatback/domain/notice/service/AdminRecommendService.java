package com.ohseat.ohseatback.domain.notice.service;


import com.ohseat.ohseatback.domain.recommend.entity.PostDomain;
import com.ohseat.ohseatback.domain.recommend.mapper.RecommendMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


// 삭제 예정
@Service
@RequiredArgsConstructor
public class AdminRecommendService {

    private final RecommendMapper recommendMapper;

    public Integer createNotice(PostDomain domain) {
//        domain.setIsNotice(true);   // 공지 자동 처리
        recommendMapper.putPost(domain);
        return domain.getPostId();
    }

    public void deletePost(Integer postId) {
        recommendMapper.deletePost(postId);
    }
}

package com.ohseat.ohseatback.domain.common.policy;

import com.ohseat.ohseatback.exception.business.UnauthorizedException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PostDeletePolicy {

    public void check(Integer authorId, Integer currentUserId, String role) {
        // 관리자면 무조건 허용
        if ("admin".equals(role)) {
            return;
        }

        // 일반 사용자는 본인 글만
        if (!Objects.equals(authorId, currentUserId)) {
            throw new UnauthorizedException("게시글 삭제 권한이 없습니다.");
        }
    }
}

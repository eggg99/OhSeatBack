package com.ohseat.ohseatback.domain.cinesquare.service;

import com.ohseat.ohseatback.domain.cinesquare.mapper.CineSquareRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ViewCountService {

    private final CineSquareRepository cineSquareRepository;

    private static final int COOKIE_EXPIRE = 60 * 60 * 24; // 1일

    public void increaseIfNeeded(Integer postId, Integer userId, Integer authorId, HttpServletRequest request, HttpServletResponse response) {
        // 작성자 제외
        if (userId != null && userId.equals(authorId)) return;

        String cookieName = "cinesquare_view_" + postId;

        boolean alreadyViewed = false;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookieName.equals(cookie.getName())) {
                    alreadyViewed = true;
                    break;
                }
            }
        }

        if (!alreadyViewed) {
            cineSquareRepository.increaseViewCount(postId);

            Cookie cookie = new Cookie(cookieName, "1");
            cookie.setPath("/");
            cookie.setMaxAge(COOKIE_EXPIRE);
            response.addCookie(cookie);
        }

    }

}

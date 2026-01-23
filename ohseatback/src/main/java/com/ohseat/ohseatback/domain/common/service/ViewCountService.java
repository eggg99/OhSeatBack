package com.ohseat.ohseatback.domain.common.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ViewCountService {

    private static final int COOKIE_EXPIRE = 60 * 60 * 24; // 1일

    /**
     * @param prefix 게시판 구분 (recommend, cinesquare, notice, event ...)
     */
    public boolean canIncrease(String prefix, Integer postId, Integer userId, Integer authorId, HttpServletRequest request, HttpServletResponse response) {
        // 작성자 제외
        if (userId != null && authorId != null && Objects.equals(userId, authorId))
            return false;

        String cookieName = prefix + "_view_" + postId;

        // 쿠키 존재 여부 확인
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookieName.equals(cookie.getName())) {
                    return false;
                }
            }
        }

        // 쿠키 생성
        Cookie cookie = new Cookie(cookieName, "1");
        cookie.setPath("/");
        cookie.setMaxAge(COOKIE_EXPIRE);
        response.addCookie(cookie);

        return true;
    }

}

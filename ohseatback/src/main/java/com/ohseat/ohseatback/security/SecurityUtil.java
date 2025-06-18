package com.ohseat.ohseatback.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 로그인한 사용자 ID 가져오는 유틸 (SecuritycontextHolder)
 */
public class SecurityUtil {

    public static Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("auth = " + authentication);
        System.out.println("auth.getPrincipal() = " + authentication.getPrincipal());

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getUserId();
        }

        return null;
    }
}

package com.ohseat.ohseatback.domain.user.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class User {
    // Getters & Setters
    private Integer userId;
    private String email;
    private String name;
    private String nickname;
    private String password;
    private String phoneNumber;
    private Timestamp createdAt;
    private String captchaToken;        // 캡차토큰필드 추가
    private String role;                // USER / ADMIN
}

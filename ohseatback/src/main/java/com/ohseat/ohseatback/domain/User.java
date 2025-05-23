package com.ohseat.ohseatback.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class User {
    // Getters & Setters
    private Integer userId;
    private String email;
    private String name;
    private String nickname;
    private String password;
    private String phoneNumber;
    private Timestamp createdAt;

    public User() {}
}

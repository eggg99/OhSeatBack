package com.ohseat.ohseatback.dto;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private Integer userId;
    private String nickname;
    private String password;
    private String phoneNumber;
}

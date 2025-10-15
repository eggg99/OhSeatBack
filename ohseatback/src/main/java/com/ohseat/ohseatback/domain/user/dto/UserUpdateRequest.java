package com.ohseat.ohseatback.domain.user.dto;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private Integer userId;
    private String nickname;
    private String phoneNumber;
}

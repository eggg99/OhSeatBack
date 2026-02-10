package com.ohseat.ohseatback.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NicknameCheckRequest {

    @NotBlank
    private String nickname;
}

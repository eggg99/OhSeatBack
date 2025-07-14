package com.ohseat.ohseatback.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PasswordChangeRequest {

//    @JsonProperty("originPwd")
//    private String currentPassword;

    private Integer userId;

    @JsonProperty("password")
    private String newPassword;

    private String password2;
}

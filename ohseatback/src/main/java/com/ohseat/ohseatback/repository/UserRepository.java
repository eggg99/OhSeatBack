package com.ohseat.ohseatback.repository;

import com.ohseat.ohseatback.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {
    // 회원가입
    void joinUser(User user);
}

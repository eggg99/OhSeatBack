package com.ohseat.ohseatback.repository;

import com.ohseat.ohseatback.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {
    // 회원가입
    void joinUser(User user);

    // 로그인
    User findByEmail(String email);

    //마이페이지 조회
    User selectUserById(Integer userId);

    // 마이페이지 수정
    int updateUserById(User user);

    // 마이페이지 삭제
    int deleteUserById(int userId);
}

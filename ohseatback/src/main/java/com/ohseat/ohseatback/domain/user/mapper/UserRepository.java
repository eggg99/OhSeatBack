package com.ohseat.ohseatback.domain.user.mapper;

import com.ohseat.ohseatback.domain.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {
    // 회원가입
    void joinUser(User user);

    // 이메일 중복 확인
    int existsByEmail(String email);

    // 닉네임 중복 확인
    int existsByNickname(String nickname);

    // 핸드폰 번호 중복 확인
    int existsByPhoneNumber(String phoneNumber);

    // 로그인
    User findByEmail(String email);

    // 이메일 찾기
    String findEmail(User user);

    // 비밀번호 찾기
    int getUserIdIfUserInfoMatched(User user);

    //마이페이지 조회
    User selectUserById(Integer userId);

    // 마이페이지 수정
    User findById(Integer userId);

    int countByNickname(User user);

    int countByPhoneNumber(User user);

    int updateUserById(User user);

    // 비밀번호 조회
    User selectUserWithPasswordById(Integer userId);
    
    // 비밀번호 수정
    int updatePasswordById(User user);

    // 마이페이지 삭제
    int deleteUserById(int userId);
}

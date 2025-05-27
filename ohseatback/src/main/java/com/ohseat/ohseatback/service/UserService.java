package com.ohseat.ohseatback.service;

import com.ohseat.ohseatback.domain.User;
import com.ohseat.ohseatback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

/*
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
*/

    // 회원가입
    public void joinUser(User user) {
        userRepository.joinUser(user);
    }

    // 마이페이지 조회
    public User getUserById(Integer userId) {
        return userRepository.selectUserById(userId);
    }

}

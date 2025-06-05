package com.ohseat.ohseatback.service;

import com.ohseat.ohseatback.domain.User;
import com.ohseat.ohseatback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

/*
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
*/

    // 회원가입
    public void joinUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.joinUser(user);
    }
    
    // 로그인
    public User findByEmail(String email, String rawPassword) {
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
            return user;
        }
        return null;
    }


    // 마이페이지 조회
    public User getUserById(Integer userId) {
        return userRepository.selectUserById(userId);
    }

}

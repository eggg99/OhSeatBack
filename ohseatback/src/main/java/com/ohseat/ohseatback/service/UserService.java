package com.ohseat.ohseatback.service;

import com.ohseat.ohseatback.domain.User;
import com.ohseat.ohseatback.dto.UserUpdateRequest;
import com.ohseat.ohseatback.exception.DuplicateResourceException;
import com.ohseat.ohseatback.exception.UserNotFoundException;
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
        // 이메일 중복 확인
        if (userRepository.existsByEmail(user.getEmail()) > 0) {
            throw new DuplicateResourceException("이미 사용 중인 이메일입니다.");
        }
        
        // 닉네임 중복 확인
        if (userRepository.existsByNickname(user.getNickname()) > 0) {
            throw new DuplicateResourceException("이미 사용 중인 닉네임입니다.");
        }
        
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.joinUser(user);
    }
    
    // 로그인
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean checkPassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

//    public User findByEmail(String email, String rawPassword) {
//        User user = userRepository.findByEmail(email);
//        if (user != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
//            return user;
//        }
//        return null;
//    }

    // 마이페이지 조회
    public User getUserById(Integer userId) {
        return userRepository.selectUserById(userId);
    }
    
    // 마이페이지 수정
    public Integer updateMyPage(Integer userId, UserUpdateRequest request) {
        User user = new User();
        user.setUserId(userId);
        user.setNickname(request.getNickname());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());

        int updatedRows = userRepository.updateUserById(user);
        if (updatedRows == 0) {
            throw new UserNotFoundException("수정할 사용자 정보가 없습니다.");
        }
        return userId;
    }
    
    
    // 마이페이지 삭제
    public void deleteMyPage(Integer userId) {
        userRepository.deleteUserById(userId);
    }

}

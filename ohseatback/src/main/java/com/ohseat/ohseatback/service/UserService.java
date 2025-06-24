package com.ohseat.ohseatback.service;

import com.ohseat.ohseatback.domain.User;
import com.ohseat.ohseatback.dto.JoinRequest;
import com.ohseat.ohseatback.dto.PasswordChangeRequest;
import com.ohseat.ohseatback.dto.UserUpdateRequest;
import com.ohseat.ohseatback.exception.business.DuplicateResourceException;
import com.ohseat.ohseatback.exception.business.InvalidPasswordException;
import com.ohseat.ohseatback.exception.business.UserNotFoundException;
import com.ohseat.ohseatback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public void joinUser(JoinRequest request) {
        // 이메일 중복 확인
        if (userRepository.existsByEmail(request.getEmail()) > 0) {
            throw new DuplicateResourceException("이미 사용 중인 이메일입니다.");
        }
        
        // 닉네임 중복 확인
        if (userRepository.existsByNickname(request.getNickname()) > 0) {
            throw new DuplicateResourceException("이미 사용 중인 닉네임입니다.");
        }

        // 핸드폰 번호 중복 확인
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber()) > 0) {
            throw new DuplicateResourceException("이미 사용 중인 핸드폰 번호입니다.");
        }
        
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setNickname(request.getNickname());
        user.setPhoneNumber(request.getPhoneNumber());
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

    // 비밀번호 수정
    public void changePassword(Integer userId, PasswordChangeRequest request) {
        User user = userRepository.selectUserWithPasswordById(userId);

        if (user == null) {
            throw new UserNotFoundException("회원을 찾을 수 없습니다.");
        }

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            System.out.println("request.getCurrentPassword() : " + request.getCurrentPassword());
            System.out.println("user.getPassword() : " + user.getPassword());
            throw new InvalidPasswordException("현재 비밀번호가 일치하지 않습니다.");
        }

        String encodedNewPassword = passwordEncoder.encode(request.getNewPassword());
        user.setPassword(encodedNewPassword);

        userRepository.updatePasswordById(user);
    }
    
    // 마이페이지 삭제
    public void deleteMyPage(Integer userId) {
        userRepository.deleteUserById(userId);
    }

}

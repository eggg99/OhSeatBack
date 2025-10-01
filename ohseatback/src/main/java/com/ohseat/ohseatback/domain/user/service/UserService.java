package com.ohseat.ohseatback.domain.user.service;

import com.ohseat.ohseatback.domain.user.entity.User;
import com.ohseat.ohseatback.domain.user.dto.JoinRequest;
import com.ohseat.ohseatback.domain.user.dto.PasswordChangeRequest;
import com.ohseat.ohseatback.domain.user.dto.UserUpdateRequest;
import com.ohseat.ohseatback.exception.business.DuplicateResourceException;
import com.ohseat.ohseatback.exception.business.NoChangesDetectedException;
import com.ohseat.ohseatback.exception.business.UserNotFoundException;
import com.ohseat.ohseatback.domain.user.mapper.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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

    // 이메일 찾기
    public String findEmail(User user) {
        return userRepository.findEmail(user);
    }

    // 비밀번호 찾기
    public Integer getUserIdIfUserInfoMatched(User user) {
        return userRepository.getUserIdIfUserInfoMatched(user);
    }

    // 마이페이지 조회
    public User getUserById(Integer userId) {
        return userRepository.selectUserById(userId);
    }

    // 마이페이지 수정
    public Integer updateMyPage(Integer userId, UserUpdateRequest request) {
        User existing = userRepository.findById(userId);
        if (existing == null) {
            throw new UserNotFoundException("수정할 사용자 정보가 없습니다.");
        }

        // 변경된 내용이 없는 경우
        boolean noChange =
                Objects.equals(existing.getNickname(), request.getNickname()) &&
                        Objects.equals(existing.getPhoneNumber(), request.getPhoneNumber());

        if (noChange) {
            throw new NoChangesDetectedException("변경된 내용이 없습니다.");
        }

        User user = new User();
        user.setUserId(userId);
        user.setNickname(request.getNickname());
        user.setPhoneNumber(request.getPhoneNumber());

        // 닉네임 중복 확인
        if (userRepository.countByNickname(user) > 0) {
            throw new DuplicateResourceException("이미 사용 중인 닉네임입니다.");
        }

        // 핸드폰 번호 중복 확인
        if (userRepository.countByPhoneNumber(user) > 0) {
            throw new DuplicateResourceException("이미 사용 중인 핸드폰 번호입니다.");
        }

        int updatedRows = userRepository.updateUserById(user);
        // user_id 존재하지 않을 경우
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

        String encodedNewPassword = passwordEncoder.encode(request.getNewPassword());
        user.setPassword(encodedNewPassword);

        userRepository.updatePasswordById(user);
    }

    // 마이페이지 삭제
    public void deleteMyPage(Integer userId) {
        userRepository.deleteUserById(userId);
    }

}

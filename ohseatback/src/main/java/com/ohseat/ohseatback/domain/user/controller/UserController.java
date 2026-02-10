package com.ohseat.ohseatback.domain.user.controller;

import com.ohseat.ohseatback.domain.user.dto.*;
import com.ohseat.ohseatback.domain.user.entity.User;
import com.ohseat.ohseatback.exception.business.InvalidPasswordException;
import com.ohseat.ohseatback.exception.business.UnauthorizedException;
import com.ohseat.ohseatback.exception.business.UserNotFoundException;
import com.ohseat.ohseatback.jwt.JwtTokenProvider;
import com.ohseat.ohseatback.security.CustomUserDetails;
import com.ohseat.ohseatback.security.SecurityUtil;
import com.ohseat.ohseatback.domain.user.service.RecaptchaService;
import com.ohseat.ohseatback.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RecaptchaService recaptchaService;

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 회원가입
     * @return
     */
    @PostMapping("/join")
    public ResponseEntity<String> joinUser(@Valid @RequestBody JoinRequest request) {
        userService.joinUser(request);
        return ResponseEntity.ok("회원가입 성공!");
    }

    /**
     * 로그인
     * @return 회원 시퀀스, 이메일, 닉네임
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody LoginRequest loginRequest) {
        // email, password가 같은 userId 값을 반환
        User user = userService.findByEmail(loginRequest.getEmail());

        if (user == null) {
            throw new UserNotFoundException("이메일이 존재하지 않습니다.");
        }

        if (!userService.checkPassword(user, loginRequest.getPassword())) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 생성 시 role 포함
        String token = jwtTokenProvider.createToken(user.getUserId(), user.getRole());

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("userId", String.valueOf(user.getUserId()));
        resultMap.put("email", user.getEmail());
        resultMap.put("nickname", user.getNickname());
        resultMap.put("role", user.getRole());
        resultMap.put("token", token);

        return ResponseEntity.ok(resultMap);
    }

    /**
     *  이메일 찾기
     */
    @PostMapping("/findEmail")
    public ResponseEntity<String> findEmail(@RequestBody User user) {
        String userEmail = userService.findEmail(user);

        if (userEmail ==  null) {
            throw new UserNotFoundException("회원을 찾을 수 없습니다.");
        }
        return ResponseEntity.ok(userEmail);
    }


    /**
     *  비밀번호 찾기
     */
    @PostMapping("/findPw")
    public ResponseEntity<Map<String, Object>> findPassword(@RequestBody User user) {
        // 캡차 토큰 검증
        if (!recaptchaService.verifyCaptcha(user.getCaptchaToken())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "캡차 인증에 실패했습니다."));
        }

        Integer userId = userService.getUserIdIfUserInfoMatched(user);

        if (userId == null) {
            throw new UserNotFoundException("회원 정보가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.createChangePwToken(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "비밀번호 찾기 성공");
        response.put("changePwToken", token);

        return ResponseEntity.ok(response);
    }

    /**
     * 마이페이지 조회
     * @return user
     */
    // 토큰 필요
    @GetMapping("/mypage")
    public ResponseEntity<User> getUserById(@RequestParam Integer userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("회원정보가 존재하지 않습니다.");
        }
        return ResponseEntity.ok(user);
    }

    /**
     * 마이페이지 수정
     * @param request
     * @return userId
     */
    @PutMapping("/mypage")
    public ResponseEntity<Integer> updateMyPage(@RequestBody UserUpdateRequest request) {
        Integer currentUserId = SecurityUtil.getCurrentUserId();
        userService.updateMyPage(currentUserId, request);
        return ResponseEntity.ok(currentUserId);
    }

    /**
     * 비밀번호 수정
     */
    @PostMapping("/changePw")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new UnauthorizedException("인증 정보가 없습니다.");
        }

        Integer userId;

        if (authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            // 로그인 상태 (기존 토큰)
            userId = userDetails.getUserId();
        } else if (authentication.getPrincipal() instanceof Integer id) {
            // 비밀번호 찾기 전용 changePwToken
            userId = id;
        } else {
            throw new UnauthorizedException("유효하지 않은 인증입니다.");
        }

        userService.changePassword(userId, request);
        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
    }

    /**
     * 마이페이지 삭제
     * @param userId
     * @return
     */
    @DeleteMapping("/mypage/{userId}")
    public ResponseEntity<String> deleteMyPage(@PathVariable Integer userId) {
        userService.deleteMyPage(userId);
        return ResponseEntity.ok("회원정보 삭제 완료");
    }

    /**
     * 닉네임 중복 확인
     */
    @PostMapping("/check-nickname")
    public ResponseEntity<Map<String, Object>> checkNickname(@RequestBody @Valid NicknameCheckRequest request) {

        Integer userId = null;

        // 로그인 상태면 userId 있음
        try {
            userId = SecurityUtil.getCurrentUserId();
        } catch (Exception ignored) {
            // 비로그인 상태 (회원가입)
        }

        boolean duplicated = userService.isNicknameDuplicated(userId, request.getNickname());

        Map<String, Object> result = new HashMap<>();
        result.put("duplicated", duplicated);
        result.put("message", duplicated ? "이미 사용 중인 닉네임입니다." : "사용 가능한 닉네임입니다.");

        return ResponseEntity.ok(result);
    }
}

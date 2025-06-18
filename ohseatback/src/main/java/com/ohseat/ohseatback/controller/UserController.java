package com.ohseat.ohseatback.controller;

import com.ohseat.ohseatback.domain.User;
import com.ohseat.ohseatback.dto.UserUpdateRequest;
import com.ohseat.ohseatback.security.SecurityUtil;
import com.ohseat.ohseatback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 회원가입
     * @return
     */

    @PostMapping("/join")
    public ResponseEntity<String> joinUser(@RequestBody User user) {
        userService.joinUser(user);
        return ResponseEntity.ok("회원가입 성공!");
    }

    /**
     * 로그인
     * @return 회원 시퀀스, 이메일, 닉네임
     */
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody User user) {
        // email, password가 같은 userId 값을 반환
        User returnUser = userService.findByEmail(user.getEmail(), user.getPassword());

        if (returnUser == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "이메일 또는 비밀번호가 일치하지 않습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("userId", String.valueOf(returnUser.getUserId()));
        resultMap.put("email", returnUser.getEmail());
        resultMap.put("nickname", returnUser.getNickname());

        return ResponseEntity.ok(resultMap);
    }

    /**
     * 마이페이지 조회
     * @return user
     */
    @GetMapping("/mypage")
    public ResponseEntity<User> getUserById(@RequestParam Integer userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
        System.out.println("currentUserId : " + currentUserId);
        userService.updateMyPage(currentUserId, request);
        return ResponseEntity.ok(currentUserId);
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
}

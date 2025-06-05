package com.ohseat.ohseatback.controller;

import com.ohseat.ohseatback.domain.User;
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
     * @return 회원 시퀀스
     */
    
//  암호화 작업 완료, 리턴 타입 User 변경 완료(확장성 고려), 예외처리 완료
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody User user) {
        // email, password가 같은 userId 값을 반환

        User returnUser = userService.findByEmail(user.getEmail(), user.getPassword());

        if (returnUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("userId", String.valueOf(returnUser.getUserId()));
        resultMap.put("email", returnUser.getEmail());
        resultMap.put("nickname", returnUser.getNickname());

        return ResponseEntity.ok(resultMap);
    }

    /**
     * 마이페이지
     * @return user 정보
     */

    // 수정, 삭제 기능 만들기
    @GetMapping("/mypage")
    public ResponseEntity<User> getUserById(@RequestParam Integer userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }
}

package com.ohseat.ohseatback.controller;

import com.ohseat.ohseatback.domain.User;
import com.ohseat.ohseatback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

/*
    @GetMapping("/")
    public void home() {
        System.out.println("home");
    }
*/

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
    
//  암호화 작업 필요, 리턴 타입 User 변경 완료(확장성 고려), 예외처리 완료
    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user) {
        // email, password가 같은 userId 값을 반환
        System.out.println("★★★ loginUser() 진입 ★★★");

        User returnUser = userService.findByEmail(user.getEmail(), user.getPassword());
//        Map<String, Integer> resultMap = new HashMap<>();
//        resultMap.put("userId", userId);

        if (returnUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(returnUser); //에러 방지
    }

/*
    // userId만 보낼 경우
    @PostMapping("/login")
    public ResponseEntity<Map<String, Integer>> loginUser(@RequestBody User user) {
        // email, password가 같은 userId 값을 반환
        System.out.println("★★★ loginUser() 진입 ★★★");

        Integer userId = userService.findByEmail(user.getEmail(), user.getPassword());
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("userId", userId);

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(resultMap); //에러 방지
    }
*/

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

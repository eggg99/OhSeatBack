package com.ohseat.ohseatback.controller;

import com.ohseat.ohseatback.domain.User;
import com.ohseat.ohseatback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity joinUser(@RequestBody User user) {
        userService.joinUser(user);
        return ResponseEntity.ok("회원가입 성공!");
    }
}

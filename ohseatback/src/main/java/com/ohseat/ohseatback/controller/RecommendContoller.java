package com.ohseat.ohseatback.controller;

import com.ohseat.ohseatback.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rcmd")
public class RecommendContoller {

    @Autowired
    private RecommendService recommendService;

    @GetMapping("/area")
    public ResponseEntity<String> getArea(){
        return ResponseEntity.ok("회원가입 성공!");
    }



}

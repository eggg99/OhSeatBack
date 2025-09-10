package com.ohseat.ohseatback.controller;

import com.ohseat.ohseatback.domain.Recommend;
import com.ohseat.ohseatback.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rcmd")
public class RecommendContoller {

    @Autowired
    private RecommendService recommendService;

    @GetMapping("/cinemaList")
    public ResponseEntity<String> getCinemaList(@RequestParam Integer multiplexId, Integer areaId){
        Recommend recommend = recommendService.getCinemaList(multiplexId, areaId);
        return ResponseEntity.ok("회원가입 성공!");
    }



}

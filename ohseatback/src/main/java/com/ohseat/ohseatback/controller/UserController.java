package com.ohseat.ohseatback.controller;

import com.ohseat.ohseatback.entity.User;
import com.ohseat.ohseatback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @GetMapping("/user")
    public List<User> userApi() {

        return userService.getUserApi();
    }

//    create
    @PostMapping("/user/create")
    public String createUserPro(User user) {
        userService.createUser(user);

//      post 하고난 뒤 표시할 url, 리액트의 서버 포트 3000
        return "redirect:http://localhost:3000/";
    }

//    delete
    @GetMapping("/user/delete")
    public String userDeletePro(Integer id){
        userService.userDelete(id);

        return "redirect:http://localhost:3000/";
    }

//    update
    @PostMapping("/user/update")
    public String userUpdatePro(Integer id, User user) {
        User userTemp = userService.userDetail(id);
        userTemp.setIntroduce(user.getIntroduce());

        userService.createUser(userTemp);
        return "redirect:http://localhost:3000/";
    }
}

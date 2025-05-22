package com.ohseat.ohseatback.service;

import com.ohseat.ohseatback.entity.User;
import com.ohseat.ohseatback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUserApi() {

        return userRepository.findAll();
    }

//    createUser 비즈니스 로직 생성
    public void createUser(User user) {
        userRepository.save(user);
    }

//    delete
    public void userDelete(Integer id) {
        userRepository.deleteById(id);
    }
    
//    update, 특정 id에 대한 데이터 가져오기
    public User userDetail(Integer id) {
        return userRepository.findById(id).get();
    }
}

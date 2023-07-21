package com.fangxiaoyun.springboot.myspringboot.service;

import com.fangxiaoyun.springboot.myspringboot.repository.UserRepository;
import com.fangxiaoyun.springboot.myspringboot.table.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getUsersByUid(Long uid) {
        return userRepository.findByUid(uid);
    }

    public List<User> getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public long countUser() {
        return userRepository.count();
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }
}

package com.fangxiaoyun.springboot.myspringboot.service;

import com.fangxiaoyun.springboot.myspringboot.repository.LoginRepository;
import com.fangxiaoyun.springboot.myspringboot.table.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {
    @Autowired
    private LoginRepository loginRepository;

    public List<Login> getLoginByPhoneNumber(String phoneNumber) {
        return loginRepository.findByPhoneNumber(phoneNumber);
    }

    public List<Login> getLoginByToken(String token) {
        return loginRepository.findByToken(token);
    }

    public List<Login> getLoginByPhoneNumberAndToken(String phoneNumber, String token) {
        return loginRepository.findByPhoneNumberAndToken(phoneNumber, token);
    }

    public void updateToken(String phoneNumber, String token, long currentTime) {
        loginRepository.updateToken(phoneNumber, token, currentTime);
    }

    public void removeToken(String phoneNumber) {
        loginRepository.deleteByPhoneNumber(phoneNumber);
    }

    public long countLogin() {
        return loginRepository.count();
    }

    public Login addLogin(Login Login) {
        return loginRepository.save(Login);
    }
}
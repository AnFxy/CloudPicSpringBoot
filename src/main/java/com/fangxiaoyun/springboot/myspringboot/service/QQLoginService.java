package com.fangxiaoyun.springboot.myspringboot.service;

import com.fangxiaoyun.springboot.myspringboot.repository.QQLoginRepository;
import com.fangxiaoyun.springboot.myspringboot.table.QQLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QQLoginService {
    @Autowired
    private QQLoginRepository qqLoginRepository;

    public List<QQLogin> getQQLoginByOpenId(String openId) {
        return qqLoginRepository.findByOpenId(openId);
    }

    public List<QQLogin> getQQLoginByAccessToken(String accessToken) {
        return qqLoginRepository.findByAccessToken(accessToken);
    }

    public List<QQLogin> getQQLoginByOpenIdAndAccessToken(String openId, String accessToken) {
        return qqLoginRepository.findByOpenIdAndAccessToken(openId, accessToken);
    }

    public void updateToken(String openId, String accessToken, long expireTime) {
        qqLoginRepository.updateToken(openId, accessToken, expireTime);
    }

    public void removeToken(String openId) {
        qqLoginRepository.deleteByOpenId(openId);
    }

    public long countLogin() {
        return qqLoginRepository.count();
    }

    public QQLogin addLogin(QQLogin qqLogin) {
        return qqLoginRepository.save(qqLogin);
    }
}
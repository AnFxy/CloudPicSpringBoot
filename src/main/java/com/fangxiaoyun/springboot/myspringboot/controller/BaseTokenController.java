package com.fangxiaoyun.springboot.myspringboot.controller;

import com.fangxiaoyun.springboot.myspringboot.common.Constants;
import com.fangxiaoyun.springboot.myspringboot.service.LoginService;
import com.fangxiaoyun.springboot.myspringboot.service.QQLoginService;
import com.fangxiaoyun.springboot.myspringboot.table.Login;
import com.fangxiaoyun.springboot.myspringboot.table.QQLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;

@Controller
public class BaseTokenController {
    @Autowired
    LoginService loginService;

    @Autowired
    QQLoginService qqLoginService;

    public Pair<Boolean, String> checkTokenOkay(HashMap<String, String> baseHead) {
        // 检测token是否存在
        if (baseHead != null && baseHead.get("token") != null && !baseHead.get("token").isEmpty()) {
            // 校验 token token长度为 54为账号密码Token， 其他为QQ登录Token TODO 做微信登录时，这里要拓展
            if (baseHead.get("token").length() == 54) {
                List<Login> loginInfo = loginService.getLoginByToken(baseHead.get("token"));
                if ((loginInfo != null) && !loginInfo.isEmpty()) {
                    // 校验 token的有效性
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - loginInfo.get(0).getCreateTime() > Constants.TOKEN_VALID_TIME) {
                        return Pair.of(false, "");
                    } else {
                        // token有效的话 核验用户上传参数数据是否正确
                        return Pair.of(true, loginInfo.get(0).getPhoneNumber());
                    }
                } else {
                    return Pair.of(false, "");
                }
            } else {
                List<QQLogin> qqLoginInfo = qqLoginService.getQQLoginByAccessToken(baseHead.get("token"));
                if ((qqLoginInfo != null) && !qqLoginInfo.isEmpty()) {
                    // 校验 token的有效性
                    long currentTime = System.currentTimeMillis();
                    if (currentTime > qqLoginInfo.get(0).getAccessTokenExpireTime()) {
                        return Pair.of(false, "");
                    } else {
                        // token有效的话 核验用户上传参数数据是否正确
                        return Pair.of(true, qqLoginInfo.get(0).getOpenId());
                    }
                } else {
                    return Pair.of(false, "");
                }
            }
        } else {
            return Pair.of(false, "");
        }
    }
}

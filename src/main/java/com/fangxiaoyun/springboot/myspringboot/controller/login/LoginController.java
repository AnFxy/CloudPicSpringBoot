package com.fangxiaoyun.springboot.myspringboot.controller.login;

import com.fangxiaoyun.springboot.myspringboot.common.Constants;
import com.fangxiaoyun.springboot.myspringboot.entity.BaseRequest;
import com.fangxiaoyun.springboot.myspringboot.entity.request.LoginMessage;
import com.fangxiaoyun.springboot.myspringboot.entity.response.LoginToken;
import com.fangxiaoyun.springboot.myspringboot.service.LoginService;
import com.fangxiaoyun.springboot.myspringboot.service.UserService;
import com.fangxiaoyun.springboot.myspringboot.table.Login;
import com.fangxiaoyun.springboot.myspringboot.table.User;
import com.fangxiaoyun.springboot.myspringboot.utils.*;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class LoginController {
    @Autowired
    UserService userService;

    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/login", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String doLogin(@RequestBody String body) {
        // 核验用户上传参数数据是否正确
        BaseRequest<LoginMessage> baseRequest =
                CheckRequestBodyUtil.instance().checkJsonStr(body, new TypeToken<LoginMessage>() {}.getType());
        if (baseRequest.isOk()) {
            // 参数核验正确，判断账号密码是否正确
            List<User> userList = userService.getUserByPhoneNumber(baseRequest.getData().getPhoneNumber());
            if (userList != null && userList.size() > 0) {
                // 已经注册过了, 判断密码是否正确
                if (userList.get(0).getPassword().equals(baseRequest.getData().getPassword())) {
                    // 账号密码校验通过，生成token
                    final String token = TokenRandomUtil.instance().generateUniqueCode();
                    // 判断登录表是否有token，有的话需要更新token，以及时间
                    List<Login> login = loginService.getLoginByPhoneNumber(baseRequest.getData().getPhoneNumber());
                    if (login != null && login.size() > 0) {
                        loginService.updateToken(
                                baseRequest.getData().getPhoneNumber(),
                                token,
                                TimeUtil.instance().getCurrentTime()
                        );
                    } else {
                        // 登录表中没有这个记录，那么需要新增
                        long count = loginService.countLogin();
                        loginService.addLogin(new Login(
                                baseRequest.getData().getPhoneNumber(),
                                token,
                                TimeUtil.instance().getCurrentTime()
                        ));
                    }
                    return SuccessResponseUtil.instance().dataResponse(new LoginToken(token));
                } else {
                    // 账号密码没有校验通过
                    return ErrorResponseUtil.instance().initResponse(Constants.PASSWORD_ERROR);
                }
            } else {
                // 没有注册过
                return ErrorResponseUtil.instance().initResponse(Constants.PHONE_NUMBER_NOT_USED);
            }
        } else {
            // 参数核验失败
            return ErrorResponseUtil.instance().initResponse(Constants.PARAM_ERROR);
        }
    }
}
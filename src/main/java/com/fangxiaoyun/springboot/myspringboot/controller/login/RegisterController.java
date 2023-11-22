package com.fangxiaoyun.springboot.myspringboot.controller.login;

import com.fangxiaoyun.springboot.myspringboot.common.Constants;
import com.fangxiaoyun.springboot.myspringboot.entity.BaseRequest;
import com.fangxiaoyun.springboot.myspringboot.entity.request.RegisterMessage;
import com.fangxiaoyun.springboot.myspringboot.service.UserService;
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
public class RegisterController {
    @Autowired
    UserService service;

    @RequestMapping(value = "/register", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String doRegister(@RequestBody String body) {
        // 核验用户上传参数数据是否正确
        BaseRequest<RegisterMessage> baseRequest =
                CheckRequestBodyUtil.instance().checkJsonStr(body, new TypeToken<RegisterMessage>() {
                }.getType());
        if (baseRequest.isOk()) {
            // 参数核验正确，判断是否已经注册过这个手机号
            List<User> userList = service.getUserByPhoneNumber(baseRequest.getData().getPhoneNumber());
            if (userList != null && userList.size() > 0) {
                // 已经注册过了
                return ErrorResponseUtil.instance().initResponse(Constants.PHONE_NUMBER_USED);
            } else {
                // 没有注册过，那么生成UID，并将用户信息插入数据库的 user_message表中
                // 注意，用户表可以将用户注销，但是不要移除用户，否则ID会出现相同
                long count = service.countUser();
                User user = new User(
                        UidUtil.instance().provide(count),
                        baseRequest.getData().getName(),
                        baseRequest.getData().getPhoneNumber(),
                        baseRequest.getData().getPassword(),
                        -1,
                        "",
                        1,
                        0,
                        0,
                        TimeUtil.instance().getCurrentTime()
                );
                User useResult = service.addUser(user);
                if (useResult != null) {
                    return SuccessResponseUtil.instance().simpleResponse();
                } else {
                    return ErrorResponseUtil.instance().initResponse(null);
                }
            }
        } else {
            // 参数核验失败
            return ErrorResponseUtil.instance().initResponse(Constants.PARAM_ERROR);
        }
    }
}

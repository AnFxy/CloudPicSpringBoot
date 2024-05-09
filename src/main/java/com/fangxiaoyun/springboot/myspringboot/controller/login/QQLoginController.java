package com.fangxiaoyun.springboot.myspringboot.controller.login;

import com.fangxiaoyun.springboot.myspringboot.common.Constants;
import com.fangxiaoyun.springboot.myspringboot.entity.BaseRequest;
import com.fangxiaoyun.springboot.myspringboot.entity.request.QQLoginMessage;
import com.fangxiaoyun.springboot.myspringboot.service.QQLoginService;
import com.fangxiaoyun.springboot.myspringboot.service.UserService;
import com.fangxiaoyun.springboot.myspringboot.table.QQLogin;
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
public class QQLoginController {
    @Autowired
    QQLoginService qqLoginService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/update_qq_login_status", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String doQQLogin(@RequestBody String body) {
        // 核验用户上传参数数据是否正确
        BaseRequest<QQLoginMessage> baseRequest =
                CheckRequestBodyUtil.instance().checkJsonStr(body, new TypeToken<QQLoginMessage>() {
                }.getType());
        if (baseRequest.isOk()) {
            // 后端不做token是否匹配的校验，交于前端处理，app每次启动自动向腾讯openAI 校验是否通过登录
            // 判断用户表中是否有此 qq 用户，如果没有需要插入一个新用户
            List<User> userList = userService.getUserByPhoneNumber(baseRequest.getData().getQqOpenId());
            if (userList == null || userList.isEmpty()) {
                // 没有注册过，将用户信息插入数据库的 user_message表中
                // 注意，用户表可以将用户注销，但是不要移除用户，否则ID会出现相同
                long count = userService.countUser();
                User user = new User(
                        UidUtil.instance().provide(count),
                        "QQ_" + baseRequest.getData().getQqOpenId().substring(0, 11),
                        baseRequest.getData().getQqOpenId(),
                        "",
                        -1,
                        "",
                        1,
                        0,
                        0,
                        TimeUtil.instance().getCurrentTime()
                );
                User useResult = userService.addUser(user);
            }

            List<QQLogin> qqLoginList = qqLoginService.getQQLoginByOpenId(baseRequest.getData().getQqOpenId());
            if (qqLoginList != null && !qqLoginList.isEmpty()) {
                // 已经注册过了, 更新Token
                qqLoginService.updateToken(
                        baseRequest.getData().getQqOpenId(),
                        baseRequest.getData().getQqAccessToken(),
                        baseRequest.getData().getQqTokenExpireTime()
                );
            } else {
                // 没有注册过 新增token
                qqLoginService.addLogin(new QQLogin(
                        baseRequest.getData().getQqOpenId(),
                        baseRequest.getData().getQqAccessToken(),
                        baseRequest.getData().getQqTokenExpireTime()
                ));
            }
            return SuccessResponseUtil.instance().simpleResponse();
        } else {
            // 参数核验失败
            return ErrorResponseUtil.instance().initResponse(Constants.PARAM_ERROR);
        }
    }
}

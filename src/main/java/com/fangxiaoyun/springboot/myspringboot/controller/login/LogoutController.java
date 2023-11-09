package com.fangxiaoyun.springboot.myspringboot.controller.login;

import com.fangxiaoyun.springboot.myspringboot.common.Constants;
import com.fangxiaoyun.springboot.myspringboot.entity.BaseRequest;
import com.fangxiaoyun.springboot.myspringboot.entity.request.LoginMessage;
import com.fangxiaoyun.springboot.myspringboot.entity.request.LogoutMessage;
import com.fangxiaoyun.springboot.myspringboot.entity.response.LoginToken;
import com.fangxiaoyun.springboot.myspringboot.service.LoginService;
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
public class LogoutController {
    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/logout", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String doLogin(@RequestBody String body) {
        // 核验用户上传参数数据是否正确
        BaseRequest<LogoutMessage> baseRequest =
                CheckRequestBodyUtil.instance().checkJsonStr(body, new TypeToken<LogoutMessage>() {}.getType());
        if (baseRequest.isOk()) {
            // 参数核验正确，判断账号、token是否正确
            List<Login> loginList =
                    loginService.getLoginByPhoneNumberAndToken(baseRequest.getData().getPhoneNumber(), baseRequest.getData().getToken());
            if (loginList != null && loginList.size() > 0) {
                // 当前账户确实处于登录中，移除token
                loginService.removeToken(baseRequest.getData().getPhoneNumber());
                return SuccessResponseUtil.instance().simpleResponse();
            } else {
                // 该账号未处于登录状态，登出操作失败
                return ErrorResponseUtil.instance().initResponse(Constants.LOGOUT_FAILED);
            }
        } else {
            // 参数核验失败
            return ErrorResponseUtil.instance().initResponse(Constants.PARAM_ERROR);
        }
    }
}

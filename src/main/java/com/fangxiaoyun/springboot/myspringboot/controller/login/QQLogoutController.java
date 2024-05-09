package com.fangxiaoyun.springboot.myspringboot.controller.login;

import com.fangxiaoyun.springboot.myspringboot.common.Constants;
import com.fangxiaoyun.springboot.myspringboot.entity.BaseRequest;
import com.fangxiaoyun.springboot.myspringboot.entity.request.QQLogoutMessage;
import com.fangxiaoyun.springboot.myspringboot.service.LoginService;
import com.fangxiaoyun.springboot.myspringboot.service.QQLoginService;
import com.fangxiaoyun.springboot.myspringboot.table.Login;
import com.fangxiaoyun.springboot.myspringboot.table.QQLogin;
import com.fangxiaoyun.springboot.myspringboot.utils.CheckRequestBodyUtil;
import com.fangxiaoyun.springboot.myspringboot.utils.ErrorResponseUtil;
import com.fangxiaoyun.springboot.myspringboot.utils.SuccessResponseUtil;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class QQLogoutController {
    @Autowired
    QQLoginService qqLoginService;

    @RequestMapping(value = "/remove_qq_login_status", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String doQQLogout(@RequestBody String body) {
        // 核验用户上传参数数据是否正确
        BaseRequest<QQLogoutMessage> baseRequest =
                CheckRequestBodyUtil.instance().checkJsonStr(body, new TypeToken<QQLogoutMessage>() {
                }.getType());
        if (baseRequest.isOk()) {
            // 参数核验正确，判断账号、token是否正确
            List<QQLogin> qqLoginList =
                    qqLoginService.getQQLoginByOpenIdAndAccessToken(
                            baseRequest.getData().getQqOpenId(),
                            baseRequest.getData().getQqAccessToken()
                    );
            if (qqLoginList != null && !qqLoginList.isEmpty()) {
                // 当前账户确实处于登录中，移除token
                qqLoginService.removeToken(baseRequest.getData().getQqOpenId());
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

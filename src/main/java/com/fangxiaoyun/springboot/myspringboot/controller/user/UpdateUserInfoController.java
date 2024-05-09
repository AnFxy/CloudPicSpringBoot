package com.fangxiaoyun.springboot.myspringboot.controller.user;

import com.fangxiaoyun.springboot.myspringboot.common.Constants;
import com.fangxiaoyun.springboot.myspringboot.controller.BaseTokenController;
import com.fangxiaoyun.springboot.myspringboot.entity.BaseRequest;
import com.fangxiaoyun.springboot.myspringboot.entity.request.UpdateUserInfoMessage;
import com.fangxiaoyun.springboot.myspringboot.service.ImageService;
import com.fangxiaoyun.springboot.myspringboot.service.LoginService;
import com.fangxiaoyun.springboot.myspringboot.service.UserService;
import com.fangxiaoyun.springboot.myspringboot.table.Image;
import com.fangxiaoyun.springboot.myspringboot.utils.CheckRequestBodyUtil;
import com.fangxiaoyun.springboot.myspringboot.utils.ErrorResponseUtil;
import com.fangxiaoyun.springboot.myspringboot.utils.MyStringUtil;
import com.fangxiaoyun.springboot.myspringboot.utils.SuccessResponseUtil;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
public class UpdateUserInfoController extends BaseTokenController {
    @Autowired
    LoginService loginService;

    @Autowired
    UserService userService;

    @Autowired
    ImageService imageService;

    @RequestMapping(value = "/update_userinfo", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String updateUserinfo(@RequestBody String body, @RequestHeader HashMap<String, String> baseHead) {
        // 检测token是否存在
        Pair<Boolean, String> tokenResult = checkTokenOkay(baseHead);
        if (tokenResult.getFirst()) {
            // token有效 判断参数数据是否正确
            BaseRequest<UpdateUserInfoMessage> baseRequest =
                    CheckRequestBodyUtil.instance().checkJsonStr(body, new TypeToken<UpdateUserInfoMessage>() {
                    }.getType());
            if (baseRequest.isOk()) {
                UpdateUserInfoMessage updateUserInfo = baseRequest.getData();
                // 参数校验通过后，如果图片链接不为空，则需要查询一下图片的ID
                long headPicId = -1;
                if (!updateUserInfo.getHeadUrl().isEmpty()) {
                    List<Image> images =
                            imageService.getImagesByName(MyStringUtil.instance().formatPicName(updateUserInfo.getHeadUrl()));
                    if (images != null && !images.isEmpty()) {
                        headPicId = images.get(0).getUid();
                    }
                }
                // 将数据更新到用户表中
                userService.updateUser(
                        tokenResult.getSecond(),
                        headPicId,
                        updateUserInfo.getName(),
                        updateUserInfo.getGender(),
                        updateUserInfo.getDes()
                );
                return SuccessResponseUtil.instance().simpleResponse();
            } else {
                // 参数核验失败
                return ErrorResponseUtil.instance().initResponse(Constants.PARAM_ERROR);
            }
        } else {
            return ErrorResponseUtil.instance().tokenInvalidResponse();
        }
    }
}
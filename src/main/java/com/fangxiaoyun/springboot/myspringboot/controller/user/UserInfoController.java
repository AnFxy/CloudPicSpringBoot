package com.fangxiaoyun.springboot.myspringboot.controller.user;

import com.fangxiaoyun.springboot.myspringboot.common.Constants;
import com.fangxiaoyun.springboot.myspringboot.controller.BaseTokenController;
import com.fangxiaoyun.springboot.myspringboot.entity.response.UserInfoResource;
import com.fangxiaoyun.springboot.myspringboot.service.ImageService;
import com.fangxiaoyun.springboot.myspringboot.service.LoginService;
import com.fangxiaoyun.springboot.myspringboot.service.UserService;
import com.fangxiaoyun.springboot.myspringboot.table.Image;
import com.fangxiaoyun.springboot.myspringboot.table.User;
import com.fangxiaoyun.springboot.myspringboot.utils.ErrorResponseUtil;
import com.fangxiaoyun.springboot.myspringboot.utils.SuccessResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
public class UserInfoController extends BaseTokenController {
    @Autowired
    LoginService loginService;

    @Autowired
    UserService userService;

    @Autowired
    ImageService imageService;

    @RequestMapping(value = "/userinfo", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String searchUserinfo(@RequestHeader HashMap<String, String> baseHead) {
        // 检测token是否存在
        Pair<Boolean, String> tokenResult = checkTokenOkay(baseHead);
        if (tokenResult.getFirst()) {
            // token有效 则根据token获取到手机号
            String phoneNumber = tokenResult.getSecond();
            // 根据手机号 从用户表中查用户信息
            List<User> userList = userService.getUserByPhoneNumber(phoneNumber);
            if (userList != null && !userList.isEmpty()) {
                String headUrl = "";
                User targetUser = userList.get(0);
                // 根据用户的head ID 找图片链接
                if (targetUser.getHeadId() != -1) {
                    List<Image> images = imageService.getImagesByUid(targetUser.getHeadId());
                    if (images != null && !images.isEmpty()) {
                        headUrl = Constants.BASE_PIC_URL + images.get(0).getName();
                    }
                }
                return SuccessResponseUtil.instance().dataResponse(
                        new UserInfoResource(
                                targetUser.getName(),
                                headUrl,
                                targetUser.getDes(),
                                targetUser.getPhoneNumber(),
                                targetUser.getGender(),
                                targetUser.getCreateTime(),
                                targetUser.getIsBlack()
                        )
                );
            } else {
                return ErrorResponseUtil.instance().initResponse(Constants.OPERATE_FAILED);
            }
        } else {
            return ErrorResponseUtil.instance().tokenInvalidResponse();
        }
    }
}

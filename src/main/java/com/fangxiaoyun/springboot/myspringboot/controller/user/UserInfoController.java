package com.fangxiaoyun.springboot.myspringboot.controller.user;

import com.fangxiaoyun.springboot.myspringboot.common.Constants;
import com.fangxiaoyun.springboot.myspringboot.entity.response.UserInfoResource;
import com.fangxiaoyun.springboot.myspringboot.service.ImageService;
import com.fangxiaoyun.springboot.myspringboot.service.LoginService;
import com.fangxiaoyun.springboot.myspringboot.service.UserService;
import com.fangxiaoyun.springboot.myspringboot.table.Image;
import com.fangxiaoyun.springboot.myspringboot.table.Login;
import com.fangxiaoyun.springboot.myspringboot.table.User;
import com.fangxiaoyun.springboot.myspringboot.utils.ErrorResponseUtil;
import com.fangxiaoyun.springboot.myspringboot.utils.SuccessResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
public class UserInfoController {
    @Autowired
    LoginService loginService;

    @Autowired
    UserService userService;

    @Autowired
    ImageService imageService;

    @RequestMapping(value = "/userinfo", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getAllAlbum(@RequestHeader HashMap<String, String> baseHead) {
        // 检测token是否存在
        if (baseHead != null && baseHead.get("token") != null && !baseHead.get("token").equals("")) {
            // 校验 token
            List<Login> loginInfo = loginService.getLoginByToken(baseHead.get("token"));
            if (loginInfo != null && loginInfo.size() > 0) {
                // 校验 token的有效性
                long currentTime = System.currentTimeMillis();
                if (currentTime - loginInfo.get(0).getCreateTime() > Constants.TOKEN_VALID_TIME) {
                    return ErrorResponseUtil.instance().tokenInvalidResponse();
                } else {
                    // token有效 则根据token获取到手机号
                    String phoneNumber = loginInfo.get(0).getPhoneNumber();
                    // 根据手机号 从用户表中查用户信息
                    List<User> userList = userService.getUserByPhoneNumber(phoneNumber);
                    if (userList != null && userList.size() > 0) {
                        String headUrl = "";
                        User targetUser = userList.get(0);
                        // 根据用户的head ID 找图片链接
                        if (targetUser.getHeadId() != -1) {
                            List<Image> images = imageService.getImagesByUid(targetUser.getHeadId());
                            if (images != null && images.size() > 0) {
                                headUrl = Constants.BASE_PIC_LOCAL_URL + images.get(0).getName();
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
                }
            } else {
                return ErrorResponseUtil.instance().tokenInvalidResponse();
            }
        } else {
            return ErrorResponseUtil.instance().tokenInvalidResponse();
        }
    }
}

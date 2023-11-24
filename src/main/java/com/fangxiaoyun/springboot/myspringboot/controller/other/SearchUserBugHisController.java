package com.fangxiaoyun.springboot.myspringboot.controller.other;

import com.fangxiaoyun.springboot.myspringboot.common.Constants;
import com.fangxiaoyun.springboot.myspringboot.entity.request.BugMessage;
import com.fangxiaoyun.springboot.myspringboot.service.BugService;
import com.fangxiaoyun.springboot.myspringboot.service.ImageService;
import com.fangxiaoyun.springboot.myspringboot.service.LoginService;
import com.fangxiaoyun.springboot.myspringboot.table.Bug;
import com.fangxiaoyun.springboot.myspringboot.table.Login;
import com.fangxiaoyun.springboot.myspringboot.utils.ErrorResponseUtil;
import com.fangxiaoyun.springboot.myspringboot.utils.SuccessResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 查询用户提交的Bug记录
 */
@Controller
public class SearchUserBugHisController {

    @Autowired
    LoginService loginService;

    @Autowired
    BugService bugService;

    @Autowired
    ImageService imageService;

    @RequestMapping(value = "/bug_history", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String addBug(@RequestHeader HashMap<String, String> baseHead) {
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
                    List<Bug> bugs = bugService.getBugsByPhoneNumber(loginInfo.get(0).getPhoneNumber());
                    List<BugMessage> busResponse = new ArrayList<>();
                    // 对于每个BUG，我们需要根据Bug中的IDs 转为对应的图片。
                    try {
                        bugs.forEach(bug -> {
                            List<String> picUrls = new ArrayList<>();
                            if (!bug.getIds().equals("")) {
                                picUrls =
                                        Arrays.stream(bug.getIds().split(","))
                                                .map(item ->
                                                        Constants.BASE_PIC_LOCAL_URL +
                                                                imageService.getImagesByUid(Long.valueOf(item)).get(0).getName()
                                                ).toList();
                            }
                            busResponse.add(new BugMessage(
                                    bug.getType(),
                                    bug.getContent(),
                                    picUrls,
                                    bug.getEmail(),
                                    bug.getStatus(),
                                    bug.getCreateTime()
                            ));
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        return ErrorResponseUtil.instance().initResponse(Constants.IMAGE_DATA_ERROR);
                    }
                    return SuccessResponseUtil.instance().dataResponse(busResponse);
                }
            } else {
                return ErrorResponseUtil.instance().tokenInvalidResponse();
            }
        } else {
            return ErrorResponseUtil.instance().tokenInvalidResponse();
        }
    }
}

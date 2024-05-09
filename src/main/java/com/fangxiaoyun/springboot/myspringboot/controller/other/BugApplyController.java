package com.fangxiaoyun.springboot.myspringboot.controller.other;

import com.fangxiaoyun.springboot.myspringboot.common.Constants;
import com.fangxiaoyun.springboot.myspringboot.controller.BaseTokenController;
import com.fangxiaoyun.springboot.myspringboot.entity.BaseRequest;
import com.fangxiaoyun.springboot.myspringboot.entity.request.BugMessage;
import com.fangxiaoyun.springboot.myspringboot.service.BugService;
import com.fangxiaoyun.springboot.myspringboot.service.ImageService;
import com.fangxiaoyun.springboot.myspringboot.service.LoginService;
import com.fangxiaoyun.springboot.myspringboot.table.Bug;
import com.fangxiaoyun.springboot.myspringboot.utils.*;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 提交BUG
 */
@Controller
public class BugApplyController extends BaseTokenController {
    @Autowired
    LoginService loginService;

    @Autowired
    BugService bugService;

    @Autowired
    ImageService imageService;

    @RequestMapping(value = "/commit_bug", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String addBug(@RequestBody String body, @RequestHeader HashMap<String, String> baseHead) {
        // 检测token是否存在
        Pair<Boolean, String> tokenResult = checkTokenOkay(baseHead);
        if (tokenResult.getFirst()) {
            // token有效的话 核验用户上传参数数据是否正确
            BaseRequest<BugMessage> baseRequest =
                    CheckRequestBodyUtil.instance().checkJsonStr(body, new TypeToken<BugMessage>() {
                    }.getType());
            if (baseRequest.isOk()) {
                BugMessage bugMessage = baseRequest.getData();
                List<String> ids = new ArrayList<>();
                String pisIdsStr = "";
                if (bugMessage.getImages() != null && !bugMessage.getImages().isEmpty()) {
                    // 根据链接获取到 图片ID
                    try {
                        ids = bugMessage.getImages().stream().map(url ->
                                String.valueOf(imageService.getImagesByName(
                                        MyStringUtil.instance().formatPicName(url)).get(0).getUid())
                        ).toList();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (!ids.isEmpty()) {
                    pisIdsStr = String.join(",", ids);
                }
                long count = bugService.allBugsCount();
                bugService.addBug(new Bug(
                        UidUtil.instance().provide(count),
                        tokenResult.getSecond(),
                        bugMessage.getType(),
                        bugMessage.getContent(),
                        pisIdsStr,
                        bugMessage.getEmail(),
                        0,
                        System.currentTimeMillis()
                ));
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

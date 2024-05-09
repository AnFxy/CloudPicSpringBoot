package com.fangxiaoyun.springboot.myspringboot.controller.other;

import com.fangxiaoyun.springboot.myspringboot.common.Constants;
import com.fangxiaoyun.springboot.myspringboot.controller.BaseTokenController;
import com.fangxiaoyun.springboot.myspringboot.entity.request.BugMessage;
import com.fangxiaoyun.springboot.myspringboot.service.BugService;
import com.fangxiaoyun.springboot.myspringboot.service.ImageService;
import com.fangxiaoyun.springboot.myspringboot.service.LoginService;
import com.fangxiaoyun.springboot.myspringboot.table.Bug;
import com.fangxiaoyun.springboot.myspringboot.utils.ErrorResponseUtil;
import com.fangxiaoyun.springboot.myspringboot.utils.SuccessResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
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
public class SearchUserBugHisController extends BaseTokenController {

    @Autowired
    LoginService loginService;

    @Autowired
    BugService bugService;

    @Autowired
    ImageService imageService;

    @RequestMapping(value = "/bug_history", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String searchBugHistory(@RequestHeader HashMap<String, String> baseHead) {
        // 检测token是否存在
        Pair<Boolean, String> tokenResult = checkTokenOkay(baseHead);
        if (tokenResult.getFirst()) {
            List<Bug> bugs = bugService.getBugsByPhoneNumber(tokenResult.getSecond());
            List<BugMessage> busResponse = new ArrayList<>();
            // 对于每个BUG，我们需要根据Bug中的IDs 转为对应的图片。
            try {
                bugs.forEach(bug -> {
                    List<String> picUrls = new ArrayList<>();
                    if (!bug.getIds().equals("")) {
                        picUrls =
                                Arrays.stream(bug.getIds().split(","))
                                        .map(item ->
                                                Constants.BASE_PIC_URL +
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
        } else {
            return ErrorResponseUtil.instance().tokenInvalidResponse();
        }
    }
}

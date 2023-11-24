package com.fangxiaoyun.springboot.myspringboot.controller.other;

import com.fangxiaoyun.springboot.myspringboot.common.Constants;
import com.fangxiaoyun.springboot.myspringboot.entity.BaseRequest;
import com.fangxiaoyun.springboot.myspringboot.entity.request.Status;
import com.fangxiaoyun.springboot.myspringboot.service.BugService;
import com.fangxiaoyun.springboot.myspringboot.utils.CheckRequestBodyUtil;
import com.fangxiaoyun.springboot.myspringboot.utils.ErrorResponseUtil;
import com.fangxiaoyun.springboot.myspringboot.utils.SuccessResponseUtil;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 更新Bug状态
 */
@Controller
public class UpdateBugController {

    @Autowired
    BugService bugService;

    @RequestMapping(value = "/update_bug_status", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String updateBugStatus(@RequestBody String body) {
        BaseRequest<Status> baseRequest =
                CheckRequestBodyUtil.instance().checkJsonStr(body, new TypeToken<Status>() {
                }.getType());
        if (baseRequest.isOk()) {
            bugService.updateBugStatus(baseRequest.getData().getUid(), baseRequest.getData().getStatus());
            return SuccessResponseUtil.instance().simpleResponse();
        } else {
            // 参数核验失败
            return ErrorResponseUtil.instance().initResponse(Constants.PARAM_ERROR);
        }
    }
}

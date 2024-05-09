package com.fangxiaoyun.springboot.myspringboot.controller.other;

import com.fangxiaoyun.springboot.myspringboot.common.Constants;
import com.fangxiaoyun.springboot.myspringboot.entity.BaseRequest;
import com.fangxiaoyun.springboot.myspringboot.entity.request.Status;
import com.fangxiaoyun.springboot.myspringboot.entity.response.BugDetailResource;
import com.fangxiaoyun.springboot.myspringboot.service.BugService;
import com.fangxiaoyun.springboot.myspringboot.service.ImageService;
import com.fangxiaoyun.springboot.myspringboot.table.Bug;
import com.fangxiaoyun.springboot.myspringboot.utils.CheckRequestBodyUtil;
import com.fangxiaoyun.springboot.myspringboot.utils.ErrorResponseUtil;
import com.fangxiaoyun.springboot.myspringboot.utils.SuccessResponseUtil;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 查询Bug
 */
@Controller
public class SearchBugController {

    @Autowired
    BugService bugService;

    @Autowired
    ImageService imageService;

    @RequestMapping(value = "/select_bug", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String selectBug(@RequestBody String body) {
        BaseRequest<Status> baseRequest =
                CheckRequestBodyUtil.instance().checkJsonStr(body, new TypeToken<Status>() {
                }.getType());
        if (baseRequest.isOk()) {
            List<Bug> bugs;
            int status = baseRequest.getData().getStatus();
            if (status == -1) {
                bugs = bugService.getAllBugs();
            } else {
                bugs = bugService.getBugsByStatus(status);
            }
            List<BugDetailResource> busResponse = new ArrayList<>();
            // 对于每个BUG，我们需要根据Bug中的IDs 转为对应的图片。
            try {
                bugs.forEach(bug -> {
                    List<String> picUrls = new ArrayList<>();
                    if (!bug.getIds().isEmpty()) {
                        picUrls =
                                Arrays.stream(bug.getIds().split(","))
                                        .map(item ->
                                                Constants.BASE_PIC_LOCAL_URL +
                                                        imageService.getImagesByUid(Long.valueOf(item)).get(0).getName()
                                        ).toList();
                    }
                    busResponse.add(new BugDetailResource(
                            bug.getUid(),
                            bug.getPhoneNumber(),
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
            // 参数核验失败
            return ErrorResponseUtil.instance().initResponse(Constants.PARAM_ERROR);
        }
    }
}

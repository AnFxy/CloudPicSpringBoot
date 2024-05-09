package com.fangxiaoyun.springboot.myspringboot.controller.version;

import com.fangxiaoyun.springboot.myspringboot.common.Constants;
import com.fangxiaoyun.springboot.myspringboot.entity.BaseRequest;
import com.fangxiaoyun.springboot.myspringboot.entity.request.VersionMessage;
import com.fangxiaoyun.springboot.myspringboot.service.VersionService;
import com.fangxiaoyun.springboot.myspringboot.table.Version;
import com.fangxiaoyun.springboot.myspringboot.utils.CheckRequestBodyUtil;
import com.fangxiaoyun.springboot.myspringboot.utils.ErrorResponseUtil;
import com.fangxiaoyun.springboot.myspringboot.utils.SuccessResponseUtil;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AddVersionController {
    @Autowired
    VersionService versionService;

    @RequestMapping(value = "/add_version", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String addVersion(@RequestBody String body) {
        // 判断参数数据是否正确
        BaseRequest<VersionMessage> baseRequest =
                CheckRequestBodyUtil.instance().checkJsonStr(body, new TypeToken<VersionMessage>() {
                }.getType());
        if (baseRequest.isOk()) {
            Version version = versionService.addVersion(
                    new Version(
                            baseRequest.getData().getVersionCode(),
                            baseRequest.getData().getVersionName(),
                            baseRequest.getData().getMiniVersionCode(),
                            baseRequest.getData().getTitle(),
                            baseRequest.getData().getContent(),
                            baseRequest.getData().getDownloadLink(),
                            System.currentTimeMillis()
                    )
            );
            return SuccessResponseUtil.instance().dataResponse(version);
        } else {
            // 参数核验失败
            return ErrorResponseUtil.instance().initResponse(Constants.PARAM_ERROR);
        }
    }
}
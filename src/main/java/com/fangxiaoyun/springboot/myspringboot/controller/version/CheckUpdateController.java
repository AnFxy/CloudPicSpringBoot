package com.fangxiaoyun.springboot.myspringboot.controller.version;

import com.fangxiaoyun.springboot.myspringboot.common.Constants;
import com.fangxiaoyun.springboot.myspringboot.service.VersionService;
import com.fangxiaoyun.springboot.myspringboot.table.Version;
import com.fangxiaoyun.springboot.myspringboot.utils.ErrorResponseUtil;
import com.fangxiaoyun.springboot.myspringboot.utils.SuccessResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CheckUpdateController {
    @Autowired
    VersionService versionService;

    @RequestMapping(value = "/check_update", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String checkVersionUpdate() {
        Version version = versionService.obtainLatestVersion();
        if (version != null) {
            return SuccessResponseUtil.instance().dataResponse(version);
        } else {
            return ErrorResponseUtil.instance().initResponse(Constants.OPERATE_FAILED);
        }
    }
}
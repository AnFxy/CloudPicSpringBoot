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

import java.util.List;

@Controller
public class AllVersionController {
    @Autowired
    VersionService versionService;

    @RequestMapping(value = "/all_version", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String checkVersionUpdate() {
        List<Version> versions = versionService.obtainAllVersions();
        if (versions != null) {
            return SuccessResponseUtil.instance().dataResponse(versions);
        } else {
            return ErrorResponseUtil.instance().initResponse(Constants.OPERATE_FAILED);
        }
    }
}
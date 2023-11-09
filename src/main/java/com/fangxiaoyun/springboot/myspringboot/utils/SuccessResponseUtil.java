package com.fangxiaoyun.springboot.myspringboot.utils;

import com.fangxiaoyun.springboot.myspringboot.common.Constants;
import com.fangxiaoyun.springboot.myspringboot.entity.BaseResponse;
import com.google.gson.Gson;

public class SuccessResponseUtil {
    private volatile static SuccessResponseUtil successResponseUtil;

    private SuccessResponseUtil() {}

    public static SuccessResponseUtil instance() {
        if (successResponseUtil == null) {
            synchronized (SuccessResponseUtil.class) {
                if (successResponseUtil == null) {
                    successResponseUtil = new SuccessResponseUtil();
                }
            }
        }
        return successResponseUtil;
    }

    public String simpleResponse() {
        return dataResponse(null);
    }

    public <T> String dataResponse(T data) {
        return new Gson().toJson(new BaseResponse<>(200, Constants.OPERATE_SUCCESS, data));
    }
}

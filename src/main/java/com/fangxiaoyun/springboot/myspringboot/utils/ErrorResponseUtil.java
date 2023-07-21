package com.fangxiaoyun.springboot.myspringboot.utils;

import com.fangxiaoyun.springboot.myspringboot.common.Constants;
import com.fangxiaoyun.springboot.myspringboot.entity.BaseResponse;
import com.google.gson.Gson;

public class ErrorResponseUtil {
    private volatile static ErrorResponseUtil errorResponseUtil;

    private ErrorResponseUtil() {}

    public static ErrorResponseUtil instance() {
        if (errorResponseUtil == null) {
            synchronized (ErrorResponseUtil.class) {
                if (errorResponseUtil == null) {
                    errorResponseUtil = new ErrorResponseUtil();
                }
            }
        }
        return errorResponseUtil;
    }

    public String initResponse(String errorText) {
        return new Gson().toJson(new BaseResponse<>(400, errorText == null ? Constants.OPERATE_FAILED : errorText, null));
    }
}

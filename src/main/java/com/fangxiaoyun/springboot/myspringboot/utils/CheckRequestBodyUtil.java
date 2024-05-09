package com.fangxiaoyun.springboot.myspringboot.utils;

import com.fangxiaoyun.springboot.myspringboot.entity.BaseRequest;
import com.fangxiaoyun.springboot.myspringboot.service.LoginService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Type;
import java.util.HashMap;

public class CheckRequestBodyUtil {
    private volatile static CheckRequestBodyUtil checkRequestBodyUtil;

    private CheckRequestBodyUtil() {
    }

    public static CheckRequestBodyUtil instance() {
        if (checkRequestBodyUtil == null) {
            synchronized (CheckRequestBodyUtil.class) {
                if (checkRequestBodyUtil == null) {
                    checkRequestBodyUtil = new CheckRequestBodyUtil();
                }
            }
        }
        return checkRequestBodyUtil;
    }

    public <T> BaseRequest<T> checkJsonStr(String jsonStr, Type type) {
        try {
            return new BaseRequest<T>(true, new Gson().fromJson(jsonStr, type));
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseRequest<T>(false, null);
        }
    }
}

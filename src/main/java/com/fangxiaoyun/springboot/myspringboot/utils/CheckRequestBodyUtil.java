package com.fangxiaoyun.springboot.myspringboot.utils;

import com.fangxiaoyun.springboot.myspringboot.entity.BaseRequest;
import com.google.gson.Gson;
import java.lang.reflect.Type;

public class CheckRequestBodyUtil {
    private volatile static CheckRequestBodyUtil checkRequestBodyUtil;

    private CheckRequestBodyUtil() {};

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

    public  <T> BaseRequest<T> checkJsonStr(String jsonStr, Type type) {
        try {
            return new BaseRequest<T>(true, new Gson().fromJson(jsonStr, type));
        } catch (Exception e){
            e.printStackTrace();
            return new BaseRequest<T>(false, null);
        }
    }
}

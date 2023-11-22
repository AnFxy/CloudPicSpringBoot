package com.fangxiaoyun.springboot.myspringboot.utils;

public class MyStringUtil {
    private volatile static MyStringUtil myStringUtil;

    private MyStringUtil() {
    }

    public static MyStringUtil instance() {
        if (myStringUtil == null) {
            synchronized (TimeUtil.class) {
                if (myStringUtil == null) {
                    myStringUtil = new MyStringUtil();
                }
            }
        }
        return myStringUtil;
    }

    // http://xxx.xx:xxx/image/abcd.jpg => abcd.jpg
    public String formatPicName(String url) {
        try {
            int targetIndex = url.lastIndexOf("/") + 1;
            return url.substring(targetIndex);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}

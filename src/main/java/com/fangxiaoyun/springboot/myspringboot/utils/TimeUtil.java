package com.fangxiaoyun.springboot.myspringboot.utils;

public class TimeUtil {

    private volatile static TimeUtil timeUtil;

    private TimeUtil() {}

    public static TimeUtil instance() {
        if (timeUtil == null) {
            synchronized (TimeUtil.class) {
                if (timeUtil == null) {
                    timeUtil = new TimeUtil();
                }
            }
        }
        return timeUtil;
    }

    public long getCurrentTime() {
        return System.currentTimeMillis();
    }
}
package com.fangxiaoyun.springboot.myspringboot.utils;

public class UidUtil {
    private volatile static UidUtil uidUtil;

    private UidUtil() {
    }

    public static UidUtil instance() {
        if (uidUtil == null) {
            synchronized (UidUtil.class) {
                if (uidUtil == null) {
                    uidUtil = new UidUtil();
                }
            }
        }
        return uidUtil;
    }

    // 加同步锁，防止并发导致数据安全问题
    public synchronized long provide(long userCount) {
        return 100000000 + userCount;
    }
}

package com.fangxiaoyun.springboot.myspringboot.utils;

import java.security.SecureRandom;

public class TokenRandomUtil {

    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int CODE_LENGTH = 50;

    private volatile static TokenRandomUtil tokenRandomUtil;

    private TokenRandomUtil() {
    }

    public static TokenRandomUtil instance() {
        if (tokenRandomUtil == null) {
            synchronized (TokenRandomUtil.class) {
                if (tokenRandomUtil == null) {
                    tokenRandomUtil = new TokenRandomUtil();
                }
            }
        }
        return tokenRandomUtil;
    }

    public String generateUniqueCode() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(ALPHABET.length());
            char randomChar = ALPHABET.charAt(index);
            sb.append(randomChar);
            if ((i + 1) % 10 == 0 && i != CODE_LENGTH - 1) {
                sb.append('_');
            }
        }
        return sb.toString();
    }
}
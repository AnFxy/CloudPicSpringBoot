package com.fangxiaoyun.springboot.myspringboot.utils;

import java.security.SecureRandom;

public class ImageRandomNameUtil {

    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int CODE_LENGTH = 24;

    private volatile static ImageRandomNameUtil imageNameUtil;

    private ImageRandomNameUtil() {}

    public static ImageRandomNameUtil instance() {
        if (imageNameUtil == null) {
            synchronized (ImageRandomNameUtil.class) {
                if (imageNameUtil == null) {
                    imageNameUtil = new ImageRandomNameUtil();
                }
            }
        }
        return imageNameUtil;
    }

    public String generateUniqueCode(String type) {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(ALPHABET.length());
            char randomChar = ALPHABET.charAt(index);
            sb.append(randomChar);
            if ((i + 1) % 6 == 0 && i != CODE_LENGTH - 1) {
                sb.append('-');
            }
        }
        return sb.toString();
    }
}

package com.fangxiaoyun.springboot.myspringboot.common;

public class Constants {

    // 表名
    public static final String USER_TABLE = "user_message";
    public static final String IMAGE_TABLE = "image_message";
    public static final String LOGIN_TABLE = "user_login";
    public static final String ALBUM_TABLE = "album_message";
    public static final String ALBUM_Image = "album_image";
    public static final String VERSION_TABLE = "version_message";
    public static final String BUG_TABLE = "bug_message";
    public static final String USER_ALBUM = "user_album";

    // 错误信息
    public static final String PARAM_ERROR = "参数传入异常";
    public static final String PHONE_NUMBER_USED = "该手机号已被注册";
    public static final String PHONE_NUMBER_NOT_USED = "该手机号未注册";
    public static final String LOGOUT_FAILED = "登出操作失败";
    public static final String PASSWORD_ERROR = "密码错误";
    public static final String IMAGE_DATA_ERROR = "图片数据解析异常";
    public static final String OPERATE_SUCCESS = "操作成功";
    public static final String OPERATE_FAILED = "操作失败";
    public static final String TOKEN_INVALID = "Token失效";

    // Token 有效期 14 天
    public static final long TOKEN_VALID_TIME = 1209600000;

    // 项目部署的图片域名
    public static final String BASE_PIC_URL = "http://124.71.83.237:8080/image/";
    public static final String BASE_PIC_LOCAL_URL = "http://172.17.10.56:8080/image/";

    // 项目图片放置路径
    public static final String PIC_LOCAL = "/Users/user/Desktop/pic/";
    public static final String PIC_REMOTE = "/projectdata/pic/";
}

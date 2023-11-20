package com.fangxiaoyun.springboot.myspringboot.table;

import com.fangxiaoyun.springboot.myspringboot.common.Constants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = Constants.USER_TABLE)
public class User {
    // 用户ID，唯一的
    @Id
    private long uid;

    // 用户名字
    @Column(name="name")
    private String name;

    // 用户手机号
    @Column(name="phone_number")
    private String phoneNumber;

    // 用户密码
    @Column(name = "password")
    private String password;

    // 用户头像
    @Column(name = "head")
    private long headId;

    // 用户简介
    @Column(name = "des")
    private String des;

    // 用户性别
    @Column(name = "gender")
    private int gender;

    // 用户是否是黑名单
    @Column(name = "is_black")
    private int isBlack;

    // 用户是否注销了账号
    @Column(name = "is_log_off")
    private int isLogOff;

    // 用户注册时间
    @Column(name = "create_time")
    private long createTime;
}

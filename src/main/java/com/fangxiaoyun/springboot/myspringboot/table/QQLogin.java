package com.fangxiaoyun.springboot.myspringboot.table;

import com.fangxiaoyun.springboot.myspringboot.common.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = Constants.QQ_LOGIN_TABLE)
public class QQLogin {
    // QQ open ID 唯一标识用户账号
    @Id
    @Column(name = "open_id")
    private String openId;

    // QQ access Token QQ登录的Token
    @Column(name = "access_token")
    private String accessToken;

    // QQ access Token 过期时间
    @Column(name = "access_token_expire_time")
    private long accessTokenExpireTime;
}

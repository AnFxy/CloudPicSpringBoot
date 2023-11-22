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
@Table(name = Constants.LOGIN_TABLE)
public class Login {

    // 用户手机号 唯一的
    @Id
    @Column(name = "phone_number")
    private String phoneNumber;

    // 用户token
    @Column(name = "token")
    private String token;

    // 创建时间 时间戳
    @Column(name = "create_time")
    private long createTime;
}

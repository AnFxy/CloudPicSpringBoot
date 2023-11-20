package com.fangxiaoyun.springboot.myspringboot.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResource {
    private String name;
    private String headUrl;
    private String des;
    private String phoneNumber;
    private int gender;
    private long registerTime;
    private int isBlack;
}

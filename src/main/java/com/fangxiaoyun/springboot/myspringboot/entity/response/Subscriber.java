package com.fangxiaoyun.springboot.myspringboot.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subscriber {
    private String headUrl;
    private String name;
    private int isOwner;
}

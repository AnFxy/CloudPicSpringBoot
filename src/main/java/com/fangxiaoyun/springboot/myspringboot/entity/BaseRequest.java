package com.fangxiaoyun.springboot.myspringboot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseRequest<T> {
    private boolean isOk;
    private T data;
}

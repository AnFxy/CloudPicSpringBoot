package com.fangxiaoyun.springboot.myspringboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BaseResponse<T> {
    private int code;
    private String message;
    private T data;
}

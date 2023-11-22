package com.fangxiaoyun.springboot.myspringboot.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoveVersionMessage {
    private long versionCode;
}

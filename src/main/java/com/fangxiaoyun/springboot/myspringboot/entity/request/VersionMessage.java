package com.fangxiaoyun.springboot.myspringboot.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VersionMessage {
    private long versionCode;
    private String versionName;
    private long miniVersionCode;
    private String title;
    private String content;
    private String downloadLink;
}

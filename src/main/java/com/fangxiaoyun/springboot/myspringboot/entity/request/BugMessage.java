package com.fangxiaoyun.springboot.myspringboot.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BugMessage {
    int type; // 类型 优化0|BUG1
    String content;
    List<String> images;
    String email;
    int status; // 审核状态 0 未处理 1 处理中 2 已完成(已修复｜已优化) 3 拒绝
    long createTime;
}

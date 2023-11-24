package com.fangxiaoyun.springboot.myspringboot.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Status {
    private long uid;
    private int status; // 审核状态 0 未处理 1 处理中 2 已完成(已修复｜已优化) 3 拒绝 -1 全部状态
}

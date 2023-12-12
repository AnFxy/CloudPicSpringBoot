package com.fangxiaoyun.springboot.myspringboot.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDetailMessage {
    private String albumId; // 相册ID
    private int type; // 0 仅退出相册 1退出并且删除相册
}

package com.fangxiaoyun.springboot.myspringboot.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAlbumMessage {
    private String albumId; // 相册ID
    private String title; // 相册标题
    private int labelId; // 相册标签
    private String faceUrl; // 相册封面链接
}

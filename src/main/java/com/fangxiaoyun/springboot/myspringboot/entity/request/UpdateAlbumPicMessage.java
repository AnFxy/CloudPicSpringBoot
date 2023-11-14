package com.fangxiaoyun.springboot.myspringboot.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAlbumPicMessage {
    private List<String> imageUrlList;
    private String albumId;
    private String updateType; // 更新相册方式 添加add 移除remove
}

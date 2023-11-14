package com.fangxiaoyun.springboot.myspringboot.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDetailResource {
    private String faceUrl;
    private int total;
    private String title;
    private int labelId;
    private Long createTime;
    private String id;
    private List<ImageResource> picList;
}

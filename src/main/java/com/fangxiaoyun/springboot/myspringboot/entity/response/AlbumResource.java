package com.fangxiaoyun.springboot.myspringboot.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumResource {
    private String faceUrl;
    private String title;
    private int labelId;
    private int total;
    private long createTime;
    private String id;
}

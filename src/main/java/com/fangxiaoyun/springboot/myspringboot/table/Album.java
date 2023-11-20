package com.fangxiaoyun.springboot.myspringboot.table;

import com.fangxiaoyun.springboot.myspringboot.common.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = Constants.ALBUM_TABLE)
public class Album {
    // 相册ID
    @Id
    @Column(name="album_id")
    private String albumId;

    // 封面图片ID
    @Column(name="face_pic_id")
    private long facePicId;

    // 标题
    @Column(name="title")
    private String title;

    // 相册标签 取值（-1、0、1、2、3 分别对应 普通、朋友、亲子、旅游、情侣）
    @Column(name="label_id")
    private int labelId;

    // 手机号
    @Column(name="phone_number")
    private String phoneNumber;

    // 创建时间
    @Column(name="create_time")
    private long createTime;
}

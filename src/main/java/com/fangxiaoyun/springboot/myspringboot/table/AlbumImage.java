package com.fangxiaoyun.springboot.myspringboot.table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 相册-图片关系映射表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AlbumImage {
    // ID
    @Id
    @Column(name="uid")
    private String uid;

    // 相册ID
    @Column(name="album_id")
    private String albumId;

    // 图片ID
    @Column(name="pic_id")
    private long picId;
}

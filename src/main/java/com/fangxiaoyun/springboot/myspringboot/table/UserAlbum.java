package com.fangxiaoyun.springboot.myspringboot.table;

import com.fangxiaoyun.springboot.myspringboot.common.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户 相册 关系映射表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = Constants.USER_ALBUM)
public class UserAlbum {
    // ID
    @Id
    @Column(name = "uid")
    private String uid;

    // 相册ID
    @Column(name = "album_id")
    private String albumId;

    // 手机号
    @Column(name = "phone_number")
    private String phoneNumber;

    // 是否是持有者
    @Column(name = "is_owner")
    private int isOwner;

    // 加入时间
    @Column(name = "create_time")
    private long createTime;
}

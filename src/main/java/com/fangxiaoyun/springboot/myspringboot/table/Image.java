package com.fangxiaoyun.springboot.myspringboot.table;

import com.fangxiaoyun.springboot.myspringboot.common.Constants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = Constants.IMAGE_TABLE)
public class Image {
    // 图片ID，唯一的
    @Id
    private Long uid;

    // 图片名字
    @Column(name="name")
    private String name;

    // 创建时间
    @Column(name="create_time")
    private Long createTime;
}

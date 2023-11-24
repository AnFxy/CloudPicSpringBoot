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
@Table(name = Constants.BUG_TABLE)
public class Bug {

    @Id
    @Column(name = "uid")
    private long uid;

    @Column(name = "phone_number")
    private String phoneNumber;

    // 类型 优化0|BUG1
    @Column(name = "type")
    private int type;

    @Column(name = "content")
    private String content;

    // 用英文逗号隔开
    @Column(name = "pic_ids")
    private String ids;

    @Column(name = "email")
    private String email;

    // 审核状态 0 未处理 1 处理中 2 已完成(已修复｜已优化) 3 拒绝
    @Column(name = "status")
    private int status;

    @Column(name = "create_time")
    private long createTime;
}

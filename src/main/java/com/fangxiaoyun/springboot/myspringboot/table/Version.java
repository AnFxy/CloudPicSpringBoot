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
@Table(name = Constants.VERSION_TABLE)
public class Version {
    // 最新版本号
    @Id
    @Column(name = "version_code")
    private long versionCode;

    // 最新版本名
    @Column(name = "version_name")
    private String versionName;

    // 最低不强制更新版本号
    @Column(name = "mini_version_code")
    private long miniVersionCode;

    // 更新提示标题
    @Column(name = "title")
    private String title;

    // 更新提示文案
    @Column(name = "content")
    private String content;

    // 下载链接
    @Column(name = "download_link")
    private String downloadLink;

    // 创建时间
    @Column(name = "create_time")
    private long create_time;

}

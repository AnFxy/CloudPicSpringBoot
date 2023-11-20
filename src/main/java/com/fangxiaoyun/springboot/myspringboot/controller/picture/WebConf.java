package com.fangxiaoyun.springboot.myspringboot.controller.picture;

import com.fangxiaoyun.springboot.myspringboot.common.Constants;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebConf extends WebMvcConfigurationSupport {

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        //映射static路径的请求到static目录下
        // 静态资源访问路径和存放路径配置
        //registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        // swagger访问配置
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/", "classpath:/META-INF/resources/webjars/");
        //通过image访问本地的图片
        registry.addResourceHandler("/image/**").addResourceLocations("file://" + Constants.PIC_LOCAL);
        registry.addResourceHandler("/apk/**").addResourceLocations("file:///projectdata/apk/");
    }

}

package com.fangxiaoyun.springboot.myspringboot.controller.picture;

import com.fangxiaoyun.springboot.myspringboot.common.Constants;
import com.fangxiaoyun.springboot.myspringboot.entity.BaseRequest;
import com.fangxiaoyun.springboot.myspringboot.entity.request.ImageMessage;
import com.fangxiaoyun.springboot.myspringboot.entity.response.ImageResource;
import com.fangxiaoyun.springboot.myspringboot.service.UserService;
import com.fangxiaoyun.springboot.myspringboot.utils.CheckRequestBodyUtil;
import com.fangxiaoyun.springboot.myspringboot.utils.ErrorResponseUtil;
import com.fangxiaoyun.springboot.myspringboot.utils.ImageRandomNameUtil;
import com.fangxiaoyun.springboot.myspringboot.utils.SuccessResponseUtil;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Base64;

@Controller
public class UploadPictureController {
    @Autowired
    UserService service;

    private String[] ALLOW_UPLOAD_TYPE = new String[] { "JPG", "PNG", "JPEG", "WEBP"};

    @RequestMapping("/upload/image")
    @ResponseBody
    public String doUpload(@RequestBody String body) {
        // 验证用户上传图片数据的合法性
        BaseRequest<ImageMessage> data =
                CheckRequestBodyUtil.instance().checkJsonStr(body, new TypeToken<ImageMessage>() {}.getType());
        if (data.isOk()) {
            // 判断是否Base64为空
            String base64 = data.getData().getBase64();
            String type = data.getData().getType();
            if (base64 == null || base64.equals("")) {
                return ErrorResponseUtil.instance().initResponse("kjb");
            } else if (type == null || type.equals("") || !Arrays.asList(ALLOW_UPLOAD_TYPE).contains(type)) {
                // 判断图片类型参数是否正确
                return ErrorResponseUtil.instance().initResponse("jhg");
            } else {
                // 解析 Base64为文件
                try {
                    byte[] decodedBytes = Base64.getDecoder().decode(base64);
                    String basePath = "/projectdata/pic/";
                    String targetPath = "http://124.71.83.237:8080/image/";
                    String fileName = ImageRandomNameUtil.instance().generateUniqueCode(type) + "." + type.toLowerCase();
                    File file = new File(basePath + fileName);
                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        fos.write(decodedBytes); // 写入解码后的字节数组到文件
                        fos.flush(); // 刷新缓冲区
                    }
                    return SuccessResponseUtil.instance().dataResponse(new ImageResource(targetPath + fileName));
                } catch (Exception e) {
                    e.printStackTrace();
                    return ErrorResponseUtil.instance().initResponse(Constants.IMAGE_DATA_ERROR);
                }
            }
        } else {
            return ErrorResponseUtil.instance().initResponse(Constants.PARAM_ERROR);
        }
    }
}

package com.fangxiaoyun.springboot.myspringboot.controller.album;

import com.fangxiaoyun.springboot.myspringboot.common.Constants;
import com.fangxiaoyun.springboot.myspringboot.entity.BaseRequest;
import com.fangxiaoyun.springboot.myspringboot.entity.request.AlbumDetailMessage;
import com.fangxiaoyun.springboot.myspringboot.service.AlbumImageService;
import com.fangxiaoyun.springboot.myspringboot.service.AlbumService;
import com.fangxiaoyun.springboot.myspringboot.service.LoginService;
import com.fangxiaoyun.springboot.myspringboot.table.Login;
import com.fangxiaoyun.springboot.myspringboot.utils.CheckRequestBodyUtil;
import com.fangxiaoyun.springboot.myspringboot.utils.ErrorResponseUtil;
import com.fangxiaoyun.springboot.myspringboot.utils.SuccessResponseUtil;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * 删除相册
 */
@Controller
public class DeleteAlbumController {
    @Autowired
    LoginService loginService;

    @Autowired
    AlbumImageService albumImageService;

    @Autowired
    AlbumService albumService;

    @RequestMapping(value = "/delete_album", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String createAlbum(@RequestBody String body, @RequestHeader HashMap<String, String> baseHead) {
        // 检测token是否存在
        if (baseHead != null && baseHead.get("token") != null && !baseHead.get("token").equals("")) {
            // 校验 token
            List<Login> loginInfo = loginService.getLoginByToken(baseHead.get("token"));
            if (loginInfo != null && loginInfo.size() > 0) {
                // 校验 token的有效性
                long currentTime = System.currentTimeMillis();
                if (currentTime - loginInfo.get(0).getCreateTime() > Constants.TOKEN_VALID_TIME) {
                    return ErrorResponseUtil.instance().tokenInvalidResponse();
                } else {
                    // token有效的话 核验用户上传参数数据是否正确
                    BaseRequest<AlbumDetailMessage> baseRequest =
                            CheckRequestBodyUtil.instance().checkJsonStr(body, new TypeToken<AlbumDetailMessage>() {
                            }.getType());
                    if (baseRequest.isOk()) {
                        // body数据合法 根据手机号和相册ID 删除相册，以及删除相册-图片表的关联
                        AlbumDetailMessage albumDetailMessage = baseRequest.getData();
                        try {
                            albumService.deleteAlbumByAlbumIdAndPhoneNumber(albumDetailMessage.getAlbumId(), loginInfo.get(0).getPhoneNumber());
                            albumImageService.deleteAlbum(albumDetailMessage.getAlbumId());
                        } catch (Exception e) {
                            e.printStackTrace();
                            return ErrorResponseUtil.instance().initResponse(Constants.OPERATE_FAILED);
                        }
                        return SuccessResponseUtil.instance().simpleResponse();
                    } else {
                        // 参数核验失败
                        return ErrorResponseUtil.instance().initResponse(Constants.PARAM_ERROR);
                    }
                }
            } else {
                return ErrorResponseUtil.instance().tokenInvalidResponse();
            }
        } else {
            return ErrorResponseUtil.instance().tokenInvalidResponse();
        }
    }
}

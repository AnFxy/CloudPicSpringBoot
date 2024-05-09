package com.fangxiaoyun.springboot.myspringboot.controller.album;

import com.fangxiaoyun.springboot.myspringboot.common.Constants;
import com.fangxiaoyun.springboot.myspringboot.controller.BaseTokenController;
import com.fangxiaoyun.springboot.myspringboot.entity.BaseRequest;
import com.fangxiaoyun.springboot.myspringboot.entity.request.AlbumDetailMessage;
import com.fangxiaoyun.springboot.myspringboot.service.AlbumImageService;
import com.fangxiaoyun.springboot.myspringboot.service.AlbumService;
import com.fangxiaoyun.springboot.myspringboot.service.LoginService;
import com.fangxiaoyun.springboot.myspringboot.service.UserAlbumService;
import com.fangxiaoyun.springboot.myspringboot.utils.CheckRequestBodyUtil;
import com.fangxiaoyun.springboot.myspringboot.utils.ErrorResponseUtil;
import com.fangxiaoyun.springboot.myspringboot.utils.SuccessResponseUtil;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * 删除相册
 */
@Controller
public class DeleteAlbumController extends BaseTokenController {
    @Autowired
    LoginService loginService;

    @Autowired
    AlbumImageService albumImageService;

    @Autowired
    AlbumService albumService;

    @Autowired
    UserAlbumService userAlbumService;

    @RequestMapping(value = "/delete_album", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String deleteAlbum(@RequestBody String body, @RequestHeader HashMap<String, String> baseHead) {
        // 检测token是否存在
        Pair<Boolean, String> tokenResult = checkTokenOkay(baseHead);
        if (tokenResult.getFirst()) {
            // token有效的话 核验用户上传参数数据是否正确
            BaseRequest<AlbumDetailMessage> baseRequest =
                    CheckRequestBodyUtil.instance().checkJsonStr(body, new TypeToken<AlbumDetailMessage>() {
                    }.getType());
            if (baseRequest.isOk()) {
                // body数据合法 根据 删除类型
                // 1.仅退出相册 删除用户-相册表的关联
                // 2. 退出并删除相册 手机号和相册ID 删除相册，以及删除相册-图片表的关联 以及删除用户-相册表的关联
                AlbumDetailMessage albumDetailMessage = baseRequest.getData();
                try {
                    if (albumDetailMessage.getType() == 1) {
                        userAlbumService.deleteUserAlbumByAlbumId(albumDetailMessage.getAlbumId(), tokenResult.getSecond());
                    } else {
                        userAlbumService.deleteUserAlbumByAlbumIdAndPhoneNumber(albumDetailMessage.getAlbumId(), tokenResult.getSecond());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return ErrorResponseUtil.instance().initResponse(Constants.OPERATE_FAILED);
                }
                return SuccessResponseUtil.instance().simpleResponse();
            } else {
                // 参数核验失败
                return ErrorResponseUtil.instance().initResponse(Constants.PARAM_ERROR);
            }
        } else {
            return ErrorResponseUtil.instance().tokenInvalidResponse();
        }
    }
}

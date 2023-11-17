package com.fangxiaoyun.springboot.myspringboot.controller.album;


import com.fangxiaoyun.springboot.myspringboot.common.Constants;
import com.fangxiaoyun.springboot.myspringboot.entity.BaseHead;
import com.fangxiaoyun.springboot.myspringboot.entity.BaseRequest;
import com.fangxiaoyun.springboot.myspringboot.entity.request.UpdateAlbumMessage;
import com.fangxiaoyun.springboot.myspringboot.service.AlbumService;
import com.fangxiaoyun.springboot.myspringboot.service.ImageService;
import com.fangxiaoyun.springboot.myspringboot.service.LoginService;
import com.fangxiaoyun.springboot.myspringboot.table.Image;
import com.fangxiaoyun.springboot.myspringboot.table.Login;
import com.fangxiaoyun.springboot.myspringboot.utils.CheckRequestBodyUtil;
import com.fangxiaoyun.springboot.myspringboot.utils.ErrorResponseUtil;
import com.fangxiaoyun.springboot.myspringboot.utils.MyStringUtil;
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

@Controller
public class UpdateAlbumController {
    @Autowired
    LoginService loginService;

    @Autowired
    ImageService imageService;

    @Autowired
    AlbumService albumService;

    @RequestMapping(value = "/update_album_detail", produces = "application/json; charset=utf-8")
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
                    BaseRequest<UpdateAlbumMessage> baseRequest =
                            CheckRequestBodyUtil.instance().checkJsonStr(body, new TypeToken<UpdateAlbumMessage>() {}.getType());
                    if (baseRequest.isOk()) {
                        UpdateAlbumMessage updateAlbumMessage = baseRequest.getData();
                        // 参数校验通过后，根据图片链接获取到 封面图片名 =》查询到图片表ID，更新相册表
                        long picId = -1;
                        if (!updateAlbumMessage.getFaceUrl().equals("")) {
                            List<Image> images =
                                    imageService.getImagesByName(MyStringUtil.instance().formatPicName(updateAlbumMessage.getFaceUrl()));
                            if (images != null && images.size() > 0) {
                                picId = images.get(0).getUid();
                            } else {
                                // 参数核验失败
                                return ErrorResponseUtil.instance().initResponse(Constants.PARAM_ERROR);
                            }
                        }
                        albumService.updateAlbum(
                                updateAlbumMessage.getTitle(),
                                updateAlbumMessage.getLabelId(),
                                picId,
                                updateAlbumMessage.getAlbumId()
                        );
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

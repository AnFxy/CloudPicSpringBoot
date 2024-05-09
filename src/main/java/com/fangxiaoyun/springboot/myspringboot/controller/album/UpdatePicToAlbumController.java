package com.fangxiaoyun.springboot.myspringboot.controller.album;

import com.fangxiaoyun.springboot.myspringboot.common.Constants;
import com.fangxiaoyun.springboot.myspringboot.controller.BaseTokenController;
import com.fangxiaoyun.springboot.myspringboot.entity.BaseRequest;
import com.fangxiaoyun.springboot.myspringboot.entity.request.UpdateAlbumPicMessage;
import com.fangxiaoyun.springboot.myspringboot.service.AlbumImageService;
import com.fangxiaoyun.springboot.myspringboot.service.ImageService;
import com.fangxiaoyun.springboot.myspringboot.service.LoginService;
import com.fangxiaoyun.springboot.myspringboot.table.AlbumImage;
import com.fangxiaoyun.springboot.myspringboot.utils.*;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
public class UpdatePicToAlbumController extends BaseTokenController {
    @Autowired
    LoginService loginService;

    @Autowired
    ImageService imageService;

    @Autowired
    AlbumImageService albumImageService;

    @RequestMapping(value = "/update_album", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String updateAlbum(@RequestBody String body, @RequestHeader HashMap<String, String> baseHead) {
        // 检测token是否存在
        Pair<Boolean, String> tokenResult = checkTokenOkay(baseHead);
        if (tokenResult.getFirst()) {
            // token有效后判断body参数是否合法
            BaseRequest<UpdateAlbumPicMessage> baseRequest =
                    CheckRequestBodyUtil.instance().checkJsonStr(body, new TypeToken<UpdateAlbumPicMessage>() {
                    }.getType());
            if (baseRequest.isOk()) {
                try {
                    List<AlbumImage> targetList = baseRequest.getData().getImageUrlList().stream().map(url ->
                            new AlbumImage(
                                    // 相册-图片表ID 长度为20
                                    ImageRandomNameUtil.instance().generateUniqueCode(20),
                                    baseRequest.getData().getAlbumId(),
                                    imageService.getImagesByName(MyStringUtil.instance().formatPicName(url)).get(0).getUid()
                            )
                    ).toList();
                    // 根据是往相册添加照片
                    if (baseRequest.getData().getUpdateType().equals("add")) {
                        targetList.forEach(albumImage -> {
                            albumImageService.addImageIntoAlbum(albumImage);
                        });
                        return SuccessResponseUtil.instance().simpleResponse();
                    } else if (baseRequest.getData().getUpdateType().equals("remove")) {
                        // 往相册移除照片
                        targetList.forEach(albumImage -> {
                            albumImageService.deleteImageOfAlbum(baseRequest.getData().getAlbumId(), albumImage.getPicId());
                        });
                        return SuccessResponseUtil.instance().simpleResponse();
                    } else {
                        // 参数核验失败
                        return ErrorResponseUtil.instance().initResponse(Constants.PARAM_ERROR);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // 图片解析异常
                    return ErrorResponseUtil.instance().initResponse(Constants.IMAGE_DATA_ERROR);
                }
            } else {
                // 参数核验失败
                return ErrorResponseUtil.instance().initResponse(Constants.PARAM_ERROR);
            }
        } else {
            return ErrorResponseUtil.instance().tokenInvalidResponse();
        }
    }
}

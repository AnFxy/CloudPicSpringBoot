package com.fangxiaoyun.springboot.myspringboot.controller.album;

import com.fangxiaoyun.springboot.myspringboot.common.Constants;
import com.fangxiaoyun.springboot.myspringboot.controller.BaseTokenController;
import com.fangxiaoyun.springboot.myspringboot.entity.BaseRequest;
import com.fangxiaoyun.springboot.myspringboot.entity.request.AlbumMessage;
import com.fangxiaoyun.springboot.myspringboot.service.AlbumService;
import com.fangxiaoyun.springboot.myspringboot.service.ImageService;
import com.fangxiaoyun.springboot.myspringboot.service.LoginService;
import com.fangxiaoyun.springboot.myspringboot.service.UserAlbumService;
import com.fangxiaoyun.springboot.myspringboot.table.Album;
import com.fangxiaoyun.springboot.myspringboot.table.Image;
import com.fangxiaoyun.springboot.myspringboot.table.UserAlbum;
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

/**
 * 创建相册
 */
@Controller
public class CreateAlbumController extends BaseTokenController {
    @Autowired
    LoginService loginService;

    @Autowired
    ImageService imageService;

    @Autowired
    AlbumService albumService;

    @Autowired
    UserAlbumService userAlbumService;

    @RequestMapping(value = "/create_album", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String createAlbum(@RequestBody String body, @RequestHeader HashMap<String, String> baseHead) {
        // 检测token是否存在
        Pair<Boolean, String> tokenResult = checkTokenOkay(baseHead);
        if (tokenResult.getFirst()) {
            // token有效的话 核验用户上传参数数据是否正确
            BaseRequest<AlbumMessage> baseRequest =
                    CheckRequestBodyUtil.instance().checkJsonStr(body, new TypeToken<AlbumMessage>() {
                    }.getType());
            if (baseRequest.isOk()) {
                // body数据合法 判断封面图片链接是否不为空字符串，如果不为空，则截取路径后的图片名字，根据名字去图片表找到ID
                AlbumMessage albumMessage = baseRequest.getData();
                // 默认封面ID
                long faceId = -1;
                if (!albumMessage.getFaceUrl().equals("")) {
                    List<Image> imageList = imageService.getImagesByName(
                            MyStringUtil.instance().formatPicName(albumMessage.getFaceUrl()));
                    if (imageList != null && imageList.size() > 0) {
                        faceId = imageList.get(0).getUid();
                    }
                }
                // 将相册 插入相册表
                String albumId = ImageRandomNameUtil.instance().generateUniqueCode(12);
                albumService.save(new Album(
                        albumId,
                        faceId,
                        albumMessage.getTitle(),
                        albumMessage.getLabelId(),
                        tokenResult.getSecond(),
                        System.currentTimeMillis()
                ));
                // 将用户-相册映射关系插入相册
                userAlbumService.attendAlbum(new UserAlbum(
                        ImageRandomNameUtil.instance().generateUniqueCode(12),
                        albumId,
                        tokenResult.getSecond(),
                        1,
                        System.currentTimeMillis()
                ));
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

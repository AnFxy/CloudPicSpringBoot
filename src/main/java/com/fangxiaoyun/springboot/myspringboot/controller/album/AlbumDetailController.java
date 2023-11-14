package com.fangxiaoyun.springboot.myspringboot.controller.album;

import com.fangxiaoyun.springboot.myspringboot.common.Constants;
import com.fangxiaoyun.springboot.myspringboot.entity.BaseHead;
import com.fangxiaoyun.springboot.myspringboot.entity.BaseRequest;
import com.fangxiaoyun.springboot.myspringboot.entity.request.AlbumDetailMessage;
import com.fangxiaoyun.springboot.myspringboot.entity.response.AlbumDetailResource;
import com.fangxiaoyun.springboot.myspringboot.entity.response.ImageResource;
import com.fangxiaoyun.springboot.myspringboot.service.AlbumImageService;
import com.fangxiaoyun.springboot.myspringboot.service.AlbumService;
import com.fangxiaoyun.springboot.myspringboot.service.ImageService;
import com.fangxiaoyun.springboot.myspringboot.service.LoginService;
import com.fangxiaoyun.springboot.myspringboot.table.Album;
import com.fangxiaoyun.springboot.myspringboot.table.AlbumImage;
import com.fangxiaoyun.springboot.myspringboot.table.Image;
import com.fangxiaoyun.springboot.myspringboot.table.Login;
import com.fangxiaoyun.springboot.myspringboot.utils.*;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取相册详情
 */
@Controller
public class AlbumDetailController {
    @Autowired
    LoginService loginService;

    @Autowired
    ImageService imageService;

    @Autowired
    AlbumService albumService;

    @Autowired
    AlbumImageService albumImageService;

    @RequestMapping(value = "/get_album_detail", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String createAlbum(@RequestBody String body, @RequestHeader BaseHead baseHead) {
        // 检测token是否存在
        if (baseHead != null && baseHead.getToken() != null && !baseHead.getToken().equals("")) {
            // 校验 token
            List<Login> loginInfo = loginService.getLoginByToken(baseHead.getToken());
            if (loginInfo != null && loginInfo.size() > 0) {
                // 校验 token的有效性
                long currentTime = System.currentTimeMillis();
                if (currentTime - loginInfo.get(0).getCreateTime() > Constants.TOKEN_VALID_TIME) {
                    return ErrorResponseUtil.instance().tokenInvalidResponse();
                } else {
                    // token有效的话 核验用户上传参数数据是否正确
                    BaseRequest<AlbumDetailMessage> baseRequest =
                            CheckRequestBodyUtil.instance().checkJsonStr(body, new TypeToken<AlbumDetailMessage>() {}.getType());
                    if (baseRequest.isOk()) {
                        // body数据合法 根据相册ID 获取到相册表中的数据
                        AlbumDetailMessage albumDetail = baseRequest.getData();
                        List<Album> albums = albumService.getAlbumByAlbumIdAndPhoneNumber(albumDetail.getAlbumId(), loginInfo.get(0).getPhoneNumber());
                        if (albums != null && albums.size() > 0) {
                            String faceUrl = "";
                            List<ImageResource> imageResourceList = new ArrayList<>();
                            List<Image> images = imageService.getImagesByUids(
                                    albumImageService.getAlbumImageByAlbumId(albumDetail.getAlbumId())
                                            .stream().map(AlbumImage::getPicId).toList()
                            );
                            images.stream().forEach(item -> {
                                imageResourceList.add(new ImageResource(Constants.BASE_PIC_URL + item.getName(), item.getCreateTime()));
                            });
                            if (albums.get(0).getFacePicId() == -1 && imageResourceList.size() > 0) {
                                faceUrl = imageResourceList.get(0).getImageUrl();
                            }
                            return SuccessResponseUtil.instance().dataResponse(new AlbumDetailResource(
                                    faceUrl,
                                    imageResourceList.size(),
                                    albums.get(0).getTitle(),
                                    albums.get(0).getLabelId(),
                                    albums.get(0).getCreateTime(),
                                    albums.get(0).getAlbumId(),
                                    imageResourceList
                            ));
                        } else {
                            // 参数核验失败
                            return ErrorResponseUtil.instance().initResponse(Constants.PARAM_ERROR);
                        }
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

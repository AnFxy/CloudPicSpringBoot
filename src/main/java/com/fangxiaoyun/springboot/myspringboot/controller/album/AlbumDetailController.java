package com.fangxiaoyun.springboot.myspringboot.controller.album;

import com.fangxiaoyun.springboot.myspringboot.common.Constants;
import com.fangxiaoyun.springboot.myspringboot.controller.BaseTokenController;
import com.fangxiaoyun.springboot.myspringboot.entity.BaseRequest;
import com.fangxiaoyun.springboot.myspringboot.entity.request.AlbumDetailMessage;
import com.fangxiaoyun.springboot.myspringboot.entity.response.AlbumDetailResource;
import com.fangxiaoyun.springboot.myspringboot.entity.response.ImageResource;
import com.fangxiaoyun.springboot.myspringboot.entity.response.Subscriber;
import com.fangxiaoyun.springboot.myspringboot.service.*;
import com.fangxiaoyun.springboot.myspringboot.table.Album;
import com.fangxiaoyun.springboot.myspringboot.table.AlbumImage;
import com.fangxiaoyun.springboot.myspringboot.table.Image;
import com.fangxiaoyun.springboot.myspringboot.table.UserAlbum;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 获取相册详情
 */
@Controller
public class AlbumDetailController extends BaseTokenController {

    @Autowired
    ImageService imageService;

    @Autowired
    AlbumService albumService;

    @Autowired
    AlbumImageService albumImageService;

    @Autowired
    UserService userService;

    @Autowired
    UserAlbumService userAlbumService;

    @RequestMapping(value = "/get_album_detail", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getAlbumDetail(@RequestBody String body, @RequestHeader HashMap<String, String> baseHead) {
        // 检测token是否存在
        Pair<Boolean, String> tokenResult = checkTokenOkay(baseHead);
        if (tokenResult.getFirst()) {
            // token有效的话 核验用户上传参数数据是否正确
            BaseRequest<AlbumDetailMessage> baseRequest =
                    CheckRequestBodyUtil.instance().checkJsonStr(body, new TypeToken<AlbumDetailMessage>() {
                    }.getType());
            if (baseRequest.isOk()) {
                // body数据合法 根据相册ID 获取到相册表中的数据
                AlbumDetailMessage albumDetail = baseRequest.getData();
                List<Album> albums = albumService.getAlbumByAlbumIdAndPhoneNumber(albumDetail.getAlbumId(), tokenResult.getSecond());
                if (albums != null && !albums.isEmpty()) {
                    String faceUrl = "";
                    List<ImageResource> imageResourceList = new ArrayList<>();
                    List<Image> images = imageService.getImagesByUids(
                            albumImageService.getAlbumImageByAlbumId(albumDetail.getAlbumId())
                                    .stream().map(AlbumImage::getPicId).toList()
                    );
                    images.forEach(item -> {
                        imageResourceList.add(new ImageResource(Constants.BASE_PIC_URL + item.getName(), item.getCreateTime()));
                    });
                    if (albums.get(0).getFacePicId() == -1 && !imageResourceList.isEmpty()) {
                        faceUrl = imageResourceList.get(0).getImageUrl();
                    }
                    // 拿这个相册的持有者信息
                    List<UserAlbum> userAlbums =
                            userAlbumService.getUserAlbumByAlbumId(albums.get(0).getAlbumId());
                    List<Subscriber> subscribers = userAlbums.stream().map(item -> {
                        List<String> headUrls = imageService.getHeadImageByPhoneNumber(item.getPhoneNumber());
                        String headUrl = !headUrls.isEmpty() ? headUrls.get(0) : "";
                        String userName = userService.getUserByPhoneNumber(item.getPhoneNumber()).get(0).getName();
                        int isOwner = item.getIsOwner();
                        return new Subscriber(headUrl, userName, isOwner);
                    }).toList();
                    return SuccessResponseUtil.instance().dataResponse(new AlbumDetailResource(
                            faceUrl,
                            imageResourceList.size(),
                            albums.get(0).getAlbumId(),
                            albums.get(0).getTitle(),
                            albums.get(0).getLabelId(),
                            albums.get(0).getCreateTime(),
                            albums.get(0).getAlbumId(),
                            imageResourceList,
                            subscribers
                    ));
                } else {
                    // 参数核验失败
                    return ErrorResponseUtil.instance().initResponse(Constants.PARAM_ERROR);
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

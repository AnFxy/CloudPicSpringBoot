package com.fangxiaoyun.springboot.myspringboot.controller.album;

import com.fangxiaoyun.springboot.myspringboot.common.Constants;
import com.fangxiaoyun.springboot.myspringboot.entity.response.AlbumResource;
import com.fangxiaoyun.springboot.myspringboot.service.AlbumImageService;
import com.fangxiaoyun.springboot.myspringboot.service.AlbumService;
import com.fangxiaoyun.springboot.myspringboot.service.ImageService;
import com.fangxiaoyun.springboot.myspringboot.service.LoginService;
import com.fangxiaoyun.springboot.myspringboot.table.Album;
import com.fangxiaoyun.springboot.myspringboot.table.AlbumImage;
import com.fangxiaoyun.springboot.myspringboot.table.Image;
import com.fangxiaoyun.springboot.myspringboot.table.Login;
import com.fangxiaoyun.springboot.myspringboot.utils.ErrorResponseUtil;
import com.fangxiaoyun.springboot.myspringboot.utils.SuccessResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 获取用户所有相册
 */
@Controller
public class AlbumsController {
    @Autowired
    LoginService loginService;

    @Autowired
    ImageService imageService;

    @Autowired
    AlbumImageService albumImageService;

    @Autowired
    AlbumService albumService;

    @RequestMapping(value = "/get_albums", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getAllAlbum(@RequestHeader HashMap<String, String> baseHead) {
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
                    // token有效后获取用户的所有相册 目前是获取的所有相册表，但相册表的相册数量、封面URL都需要去查另外两个表
                    List<Album> albums = albumService.getAlbumsByPhoneNumber(loginInfo.get(0).getPhoneNumber());
                    List<AlbumResource> albumResources = new ArrayList<>();
                    for (Album album : albums) {
                        int count = 0; // 相册数量
                        String faceUrl = ""; // 相册封面
                        List<AlbumImage> albumImages = albumImageService.getAlbumImageByAlbumId(album.getAlbumId());
                        if (albumImages != null && albumImages.size() > 0) {
                            // 更新相册数量
                            count = albumImages.size();
                            // 如果相册封面ID为 -1 （用户未设置封面，那么默认用用户最新的一张图片作为封面）
                            if (album.getFacePicId() == -1) {
                                List<Image> targetImages = imageService.getImagesByUid(album.getFacePicId());
                                if (targetImages != null && targetImages.size() > 0) {
                                    faceUrl = Constants.BASE_PIC_LOCAL_URL + targetImages.get(0).getName();
                                }
                            }
                        }
                        albumResources.add(new AlbumResource(
                                faceUrl, album.getTitle(), album.getLabelId(), count,
                                album.getCreateTime(), album.getAlbumId())
                        );
                    }
                    return SuccessResponseUtil.instance().dataResponse(albumResources);
                }
            } else {
                return ErrorResponseUtil.instance().tokenInvalidResponse();
            }
        } else {
            return ErrorResponseUtil.instance().tokenInvalidResponse();
        }
    }
}

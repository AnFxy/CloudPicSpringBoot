package com.fangxiaoyun.springboot.myspringboot.service;

import com.fangxiaoyun.springboot.myspringboot.repository.AlbumImageRepository;
import com.fangxiaoyun.springboot.myspringboot.repository.IAlbumRepository;
import com.fangxiaoyun.springboot.myspringboot.repository.UserAlbumRepository;
import com.fangxiaoyun.springboot.myspringboot.table.UserAlbum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAlbumService {
    @Autowired
    UserAlbumRepository userAlbumRepository;

    @Autowired
    IAlbumRepository iAlbumRepository;

    @Autowired
    AlbumImageRepository albumImageRepository;
    // 查询用户所持有的相册
    public List<UserAlbum> getUserAlbumByPhoneNumber(String phoneNumber) {
        return userAlbumRepository.findByPhoneNumberOrderByCreateTime(phoneNumber);
    }

    public List<UserAlbum> getUserAlbumByAlbumId(String albumId){
        return userAlbumRepository.findByAlbumId(albumId);
    }

    // 退出并删除相册
    public void deleteUserAlbumByAlbumId(String albumId, String phoneNumber){
        userAlbumRepository.deleteByAlbumId(albumId);
        iAlbumRepository.deleteByAlbumIdAndPhoneNumber(albumId, phoneNumber);
        albumImageRepository.deleteByAlbumId(albumId);
    }

    // 退出相册
    public void deleteUserAlbumByAlbumIdAndPhoneNumber(String albumId, String phoneNumber){
        userAlbumRepository.deleteByAlbumIdAndPhoneNumber(albumId, phoneNumber);
    }

    // 加入相册
    public void attendAlbum(UserAlbum userAlbum) {
        userAlbumRepository.save(userAlbum);
    }
}

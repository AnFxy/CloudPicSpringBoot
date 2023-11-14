package com.fangxiaoyun.springboot.myspringboot.service;

import com.fangxiaoyun.springboot.myspringboot.repository.IAlbumRepository;
import com.fangxiaoyun.springboot.myspringboot.table.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService {
    @Autowired
    private IAlbumRepository albumRepository;

    public List<Album> getAlbumByAlbumId(String albumId) {
        return albumRepository.findByAlbumId(albumId);
    }

    public List<Album> getAlbumByAlbumIdAndPhoneNumber(String albumId, String phoneNumber) {
        return albumRepository.findByAlbumIdAndPhoneNumber(albumId, phoneNumber);
    }

    public void deleteAlbumByAlbumIdAndPhoneNumber(String albumId, String phoneNumber) {
        albumRepository.deleteByAlbumIdAndPhoneNumber(albumId, phoneNumber);
    }

    public List<Album> getAlbumsByPhoneNumber(String phoneNumber) {
        return albumRepository.findByPhoneNumber(phoneNumber);
    }

    public void updateAlbum(String title, int labelId, long facePicId, String albumId) {
        albumRepository.updateAlbum(title, labelId, facePicId, albumId);
    }

    public Album save(Album album) {
        return albumRepository.save(album);
    }
}

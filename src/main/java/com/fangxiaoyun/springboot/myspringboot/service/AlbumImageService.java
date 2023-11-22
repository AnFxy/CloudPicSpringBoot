package com.fangxiaoyun.springboot.myspringboot.service;

import com.fangxiaoyun.springboot.myspringboot.repository.AlbumImageRepository;
import com.fangxiaoyun.springboot.myspringboot.table.AlbumImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumImageService {
    @Autowired
    private AlbumImageRepository albumImageRepository;

    public List<AlbumImage> getAlbumImageByUid(String uid) {
        return albumImageRepository.findByUid(uid);
    }

    public List<AlbumImage> getAlbumImageByAlbumId(String albumId) {
        return albumImageRepository.findByAlbumId(albumId);
    }

    public void deleteAlbum(String albumId) {
        albumImageRepository.deleteByAlbumId(albumId);
    }

    public void deleteImageOfAlbum(String albumId, long picId) {
        albumImageRepository.deleteByAlbumIdAndPicId(albumId, picId);
    }

    public AlbumImage addImageIntoAlbum(AlbumImage albumImage) {
        return albumImageRepository.save(albumImage);
    }
}

package com.fangxiaoyun.springboot.myspringboot.service;

import com.fangxiaoyun.springboot.myspringboot.repository.ImageRepository;
import com.fangxiaoyun.springboot.myspringboot.table.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    public List<Image> getImagesByUid(Long uid) {
        return imageRepository.findByUid(uid);
    }

    public List<Image> getImagesByName(String name) { return imageRepository.findByName(name); }

    public List<Image> getImagesByUids(List<Long> uids) {
        return imageRepository.findByUidIn(uids);
    }

    public long count() {
        return imageRepository.count();
    }

    public Image addImage(Image image) {
        return imageRepository.save(image);
    }
}

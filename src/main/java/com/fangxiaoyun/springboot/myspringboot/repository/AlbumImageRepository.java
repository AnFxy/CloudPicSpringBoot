package com.fangxiaoyun.springboot.myspringboot.repository;

import com.fangxiaoyun.springboot.myspringboot.table.AlbumImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumImageRepository extends JpaRepository<AlbumImage, Long> {
    List<AlbumImage> findByUid(String uid);

    List<AlbumImage> findByAlbumId(String albumId);

    void deleteByAlbumId(String albumId);

    void deleteByAlbumIdAndPicId(String albumId, long picId);
}

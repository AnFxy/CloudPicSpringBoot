package com.fangxiaoyun.springboot.myspringboot.repository;

import com.fangxiaoyun.springboot.myspringboot.table.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface IAlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findByAlbumId(String albumId);

    List<Album> findByAlbumIdAndPhoneNumber(String albumId, String phoneNumber);

    List<Album> findByPhoneNumber(String phoneNumber);

    void deleteByAlbumIdAndPhoneNumber(String albumId, String phoneNumber);

    long count();

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,
            value = "update album_message set title = :title, label_id = :labelId, face_pic_id = :facePicId where album_id = :albumId")
    void updateAlbum(@Param("title") String title, @Param("labelId") int labelId, @Param("facePicId") long facePicId, @Param("albumId") String albumId);

    Album save(Album album);
}
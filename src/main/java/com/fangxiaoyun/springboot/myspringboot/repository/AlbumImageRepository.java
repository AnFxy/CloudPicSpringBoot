package com.fangxiaoyun.springboot.myspringboot.repository;

import com.fangxiaoyun.springboot.myspringboot.table.AlbumImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface AlbumImageRepository extends JpaRepository<AlbumImage, Long> {
    List<AlbumImage> findByUid(String uid);

    List<AlbumImage> findByAlbumId(String albumId);

    void deleteByAlbumId(String albumId);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,
            value = "delete from album_image where album_id = :albumId and pic_id = :picId")
    void deleteByAlbumIdAndPicId(String albumId, long picId);
}

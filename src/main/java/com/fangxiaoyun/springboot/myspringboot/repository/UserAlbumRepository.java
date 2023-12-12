package com.fangxiaoyun.springboot.myspringboot.repository;

import com.fangxiaoyun.springboot.myspringboot.table.UserAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAlbumRepository extends JpaRepository<UserAlbum, Long> {

    List<UserAlbum> findByPhoneNumberOrderByCreateTime(String phoneNumber);

    List<UserAlbum> findByAlbumId(String albumId);

    void deleteByAlbumId(String albumId);

    void deleteByAlbumIdAndPhoneNumber(String albumId, String phoneNumber);
}

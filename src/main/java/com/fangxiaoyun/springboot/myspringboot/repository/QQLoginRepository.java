package com.fangxiaoyun.springboot.myspringboot.repository;

import com.fangxiaoyun.springboot.myspringboot.table.Login;
import com.fangxiaoyun.springboot.myspringboot.table.QQLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface QQLoginRepository extends JpaRepository<QQLogin, Long> {
    List<QQLogin> findByOpenId(String openId);

    List<QQLogin> findByAccessToken(String accessToken);

    List<QQLogin> findByOpenIdAndAccessToken(String openId, String accessToken);

    void deleteByOpenId(String openId);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,
            value = "update qq_user_login set access_token = :accessToken, access_token_expire_time = :accessTokenExpireTime where open_id = :openId")
    void updateToken(@Param("openId") String openId, @Param("accessToken") String accessToken, @Param("accessTokenExpireTime") long accessTokenExpireTime);
}
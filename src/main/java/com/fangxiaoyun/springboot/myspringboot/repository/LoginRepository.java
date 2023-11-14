package com.fangxiaoyun.springboot.myspringboot.repository;

import com.fangxiaoyun.springboot.myspringboot.table.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface LoginRepository extends JpaRepository<Login, Long> {
    List<Login> findByPhoneNumber(String phoneNumber);
    List<Login> findByToken(String token);
    List<Login> findByPhoneNumberAndToken(String phoneNumber, String token);
    void deleteByPhoneNumber(String phoneNumber);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,
            value = "update user_login set token = :token, create_time = :createTime where phone_number = :phoneNumber")
    void updateToken(@Param("phoneNumber") String phoneNumber,@Param("token") String  token, @Param("createTime") long createTime);

    long count();

    Login save(Login login);
}
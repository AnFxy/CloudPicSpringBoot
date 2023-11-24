package com.fangxiaoyun.springboot.myspringboot.repository;

import com.fangxiaoyun.springboot.myspringboot.table.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUid(Long uid);

    List<User> findByPhoneNumber(String phoneNumber);

    List<User> findByPhoneNumberAndPassword(String phoneNumber, String password);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,
            value = "update user_message set head = :headId, name = :nickName, gender = :gender, des = :des where phone_number = :phoneNumber")
    void updateUserInfo(
            @Param("phoneNumber") String phoneNumber,
            @Param("headId") long headId,
            @Param("nickName") String nickName,
            @Param("gender") int gender,
            @Param("des") String des
    );
}

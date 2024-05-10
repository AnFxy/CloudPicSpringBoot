package com.fangxiaoyun.springboot.myspringboot.repository;

import com.fangxiaoyun.springboot.myspringboot.table.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByUid(Long uid);

    List<Image> findByName(String name);

    List<Image> findByUidIn(List<Long> uids);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,
            value = "select image_message.name from image_message," +
                    " user_message where user_message.phone_number = :phoneNumber and image_message.uid = user_message.head")
    List<String> findHeadByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}

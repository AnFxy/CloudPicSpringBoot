package com.fangxiaoyun.springboot.myspringboot.repository;

import com.fangxiaoyun.springboot.myspringboot.table.Bug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface BugRepository extends JpaRepository<Bug, Long> {

    List<Bug> findByPhoneNumber(String phoneNumber);

    List<Bug> findByStatus(int status);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,
            value = "update bug_message set status = :status where uid = :uid")
    void updateBugMessage(
            @Param("status") int status,
            @Param("uid") long uid
    );
}
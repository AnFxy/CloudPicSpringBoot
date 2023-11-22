package com.fangxiaoyun.springboot.myspringboot.repository;

import com.fangxiaoyun.springboot.myspringboot.table.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface VersionRepository extends JpaRepository<Version, Long> {

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,
            value = "select * from version_message order by create_time desc limit 1")
    Version selectLatestVersion();

    Version save(Version version);

    void deleteVersionByVersionCode(long versionCode);

    List<Version> findAll();
}

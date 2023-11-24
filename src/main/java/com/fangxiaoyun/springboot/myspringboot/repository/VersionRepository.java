package com.fangxiaoyun.springboot.myspringboot.repository;

import com.fangxiaoyun.springboot.myspringboot.table.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface VersionRepository extends JpaRepository<Version, Long> {

    @Query(nativeQuery = true,
            value = "select * from version_message order by create_time desc limit 1")
    Version selectLatestVersion();

    void deleteVersionByVersionCode(long versionCode);
}

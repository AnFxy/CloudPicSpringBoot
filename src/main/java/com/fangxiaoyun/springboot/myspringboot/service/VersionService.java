package com.fangxiaoyun.springboot.myspringboot.service;

import com.fangxiaoyun.springboot.myspringboot.repository.VersionRepository;
import com.fangxiaoyun.springboot.myspringboot.table.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VersionService {
    @Autowired
    private VersionRepository versionRepository;

    public Version obtainLatestVersion() {
        return versionRepository.selectLatestVersion();
    }

    public List<Version> obtainAllVersions() {
        return versionRepository.findAll();
    }

    public Version addVersion(Version version) {
        return versionRepository.save(version);
    }

    public void removeVersionByVersionCode(long versionCode) {
        versionRepository.deleteVersionByVersionCode(versionCode);
    }
}

package com.fangxiaoyun.springboot.myspringboot.service;

import com.fangxiaoyun.springboot.myspringboot.repository.BugRepository;
import com.fangxiaoyun.springboot.myspringboot.table.Bug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BugService {
    @Autowired
    private BugRepository bugRepository;

    public List<Bug> getAllBugs() {
        return bugRepository.findAll();
    }

    public List<Bug> getBugsByPhoneNumber(String phoneNumber) {
        return bugRepository.findByPhoneNumber(phoneNumber);
    }

    public List<Bug> getBugsByStatus(int status) {
        return bugRepository.findByStatus(status);
    }

    public void updateBugStatus(long uid, int status){
        bugRepository.updateBugMessage(status, uid);
    }

    public void addBug(Bug bug) {
        bugRepository.save(bug);
    }

    public long allBugsCount() {
        return bugRepository.count();
    }
}

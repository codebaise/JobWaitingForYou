package com.home.closematch.entity;

import com.home.closematch.mapper.AccountMapper;
import com.home.closematch.mapper.JobSeekerMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@SpringBootTest
public class CmAccountTest {

    @Autowired
    private AccountMapper cmAccountMapper;
    @Autowired
    private JobSeekerMapper cmJobSeekerMapper;

    @Test
    public void addAccount(){
//        List<JobSeeker> cmJobSeekers = cmJobSeekerMapper.selectList(null);
//        for (JobSeeker cmJobSeeker : cmJobSeekers) {
//            Random random = new Random(22);
//            UUID uuid = UUID.randomUUID();
//            Account cmAccount = new Account(null, uuid.toString().substring(0, 5),
//                    uuid.toString().substring(0, 5), (int) Math.round(Math.random()),
//                    cmJobSeeker.getId());
//            cmAccountMapper.insert(cmAccount);
//        }
    }
}

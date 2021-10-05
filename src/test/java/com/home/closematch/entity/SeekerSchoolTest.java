package com.home.closematch.entity;

import com.home.closematch.mapper.JobSeekerMapper;
import com.home.closematch.mapper.SeekerSchoolMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@SpringBootTest
public class SeekerSchoolTest {

    @Autowired
    private JobSeekerMapper jobSeekerMapper;

    @Autowired
    private SeekerSchoolMapper seekerSchoolMapper;

    @Test
    public void addSchool(){
        List<JobSeeker> cmJobSeekers = jobSeekerMapper.selectList(null);
        Random random = new Random(22);
//
//        for (JobSeeker cmJobSeeker : cmJobSeekers) {
//            UUID uuid = UUID.randomUUID();
//
//            seekerSchoolMapper.insert(new SeekerSchool(null, uuid.toString().substring(0, 5), uuid.toString().substring(0, 5),
//                    random.nextInt(5), new Date(), new Date(), cmJobSeeker.getId()));
//
//        }

    }
}

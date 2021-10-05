package com.home.closematch.entity;

import com.home.closematch.mapper.JobSeekerMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@SpringBootTest
public class CmJobSeekerTest {

    @Autowired
    private JobSeekerMapper jobSeekerMapper;

    @Test
    public void getJobSeekers(){
        List<JobSeeker> jobSeekers = jobSeekerMapper.selectList(null);
        for (JobSeeker jobSeeker : jobSeekers) {
            System.out.println(jobSeeker.getId());
        }
    }
    @Test
    public void addJobSeeker(){
        Random random = new Random(22);
        for (int i=0; i<20; i++){
            UUID uuid = UUID.randomUUID();
            /**
             * String name, Integer age, Integer userIdentity,
             * Integer currentStatus, String expectPosition, String expectCity,
             * String advantage, BigDecimal minExpectSalary, BigDecimal maxExpectSalary
             */
            int i1 = random.nextInt();

            JobSeeker jobSeeker = new JobSeeker(uuid.toString().substring(0, 5).toUpperCase(),
                    random.nextInt(50), (int) Math.round(Math.random()),
                    random.nextInt(4),"Java开发", "西安",
                    "confidence", i1, i1 + 10);
            jobSeekerMapper.insert(jobSeeker);
        }
    }
}

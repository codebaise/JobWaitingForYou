package com.home.closematch.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.home.closematch.entity.dto.SeekerAccountSchoolDTO;
import com.home.closematch.utils.CommonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CmJobSeekerMapperTest {

    @Autowired
    private JobSeekerMapper cmJobSeekerMapper;
    @Test
    public void getSeekerListWithAccountTest(){
        IPage<SeekerAccountSchoolDTO> seekerAccountSchoolDTOIPage = cmJobSeekerMapper.selectSeekerListWithAccount(new Page<>(3, CommonUtils.perPageSize));
        System.out.println(seekerAccountSchoolDTOIPage);
    }
}

package com.home.closematch.service.impl;

import com.home.closematch.entity.Position;
import com.home.closematch.entity.vo.CompanyDetailInfoVo;
import com.home.closematch.pojo.PagePositionHr;
import com.home.closematch.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CompanyServiceImplTest {
    @Autowired
    private CompanyService companyService;

    @Test
    void getCompanyDetailInfoByCompanyId() {
        CompanyDetailInfoVo companyDetailInfoByCompanyId = companyService.getCompanyDetailInfoByCompanyId(12L);
        System.out.println();
    }

    @Test
    void getCompanyPositions() {
        PagePositionHr companyPositions = companyService.getCompanyPositions(1L, true, 1);
        System.out.println(companyPositions);

    }

    @Test
    void getCompanysList() {
        companyService.getCompanysList(1);
        System.out.println();
    }
}
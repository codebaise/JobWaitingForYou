package com.home.closematch.entity;

import com.home.closematch.mapper.CompanyMapper;
import com.home.closematch.utils.CompanyUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class CompanyTest {
    @Autowired
    private CompanyMapper companyMapper;
    @Test
    public void addCompany(){
        for (int i=0; i < 20; i++){
            Company company = new Company();
            company.setAuditStatus(CompanyUtils.COMPANY_AUDIT_TYPE_WAIT);
            String s = UUID.randomUUID().toString();
            company.setDomain(s.substring(0, 10).toUpperCase());
            company.setName(s.substring(0, 10).toUpperCase());
            company.setDescription(s.toUpperCase());
            company.setScale("200-2000");
            company.setSimpleName(s.substring(0, 5).toUpperCase());
            companyMapper.insert(company);
        }
    }
}

package com.home.closematch.entity;

import com.home.closematch.mapper.SysAdminMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CmAdminTest {

    @Autowired
    private SysAdminMapper sysAdminMapper;
    @Test
    public void addAdmin(){
        SysAdmin cmSysAdmin = new SysAdmin();
        cmSysAdmin.setUsername("admin");
        cmSysAdmin.setPassword("admin123");
        sysAdminMapper.insert(cmSysAdmin);
    }
}

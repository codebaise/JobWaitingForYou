package com.home.closematch.controller.admin;

import com.home.closematch.entity.SysAdmin;
import com.home.closematch.pojo.Msg;
import com.home.closematch.exception.ServiceErrorException;
import com.home.closematch.service.CompanyService;
import com.home.closematch.service.HumanresoucresService;
import com.home.closematch.service.JobSeekerService;
import com.home.closematch.service.SysAdminService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Api(value = "AdminAccount", tags = "后台管理")
@RestController
public class AdminAccountController {

    @Autowired
    private SysAdminService sysAdminService;

    // 改变账户状态
    @PutMapping("/backStageManagement/account/{accountId}")
    public Msg changeUserAccountStatus(@PathVariable("accountId") Long accountId){

        sysAdminService.changeUserAccountUserByAccountId(accountId);
        return Msg.success("success");


    }

//    @CrossOrigin(value = "http://localhost:9528")
    @PostMapping(value = "/backStageManagement/login")
    public Msg login(@RequestBody(required = false) SysAdmin cmSysAdmin){
        if (cmSysAdmin == null)
            return Msg.fail(400, "Other Error");

        return Msg.success("success").add("token", sysAdminService.loginToBackStage(cmSysAdmin.getUsername(), cmSysAdmin.getPassword()));

    }
}


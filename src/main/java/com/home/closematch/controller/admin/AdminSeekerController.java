package com.home.closematch.controller.admin;

import com.home.closematch.pojo.Msg;
import com.home.closematch.service.JobSeekerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "AdmimSeeker", tags = "后台管理")
@RestController
public class AdminSeekerController {
    @Autowired
    private JobSeekerService jobSeekerService;

    // 获取 seeker 列表 并连接查询 account状态
    @ApiOperation(value = "获取 seeker 列表 并连接查询 account状态")
    @GetMapping("/backStageManagement/seekers")
    public Msg getSeekerWithAccountInfo(@RequestParam(required = false, defaultValue="1") Integer page){
        return Msg.success("success").add("items", jobSeekerService.getSeekerListWithAccount(page));
    }
}

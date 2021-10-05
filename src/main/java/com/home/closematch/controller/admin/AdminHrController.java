package com.home.closematch.controller.admin;

import com.home.closematch.pojo.Msg;
import com.home.closematch.service.HumanresoucresService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "AdminHr", tags = "后台管理")
@RestController
public class AdminHrController {
    @Autowired
    private HumanresoucresService humanresoucresService;
    // 获取 hr 列表, 并连接查询 响应的工资信息
    @GetMapping("/backStageManagement/hrs")
    public Msg getHrWithCompany(@RequestParam(required = false, defaultValue="1") Integer page){
        return Msg.success("success").add("items", humanresoucresService.getHrWithCompanyList(page));
    }
}

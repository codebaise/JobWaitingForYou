package com.home.closematch.controller;

import com.home.closematch.entity.Account;
import com.home.closematch.entity.Company;
import com.home.closematch.entity.Humanresoucres;
import com.home.closematch.pojo.Msg;
import com.home.closematch.service.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "公司相关")
@RestController
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @ApiOperation(value = "获取公司详情信息",
            notes = "公司详情页面调用, 基础功能, 通过companyId获取公司基础信息")
    @GetMapping("/company/{companyId}")
    public Msg getCompanyDetailInfo(@PathVariable Long companyId){
        return Msg.success().add("detailInfo", companyService.getCompanyDetailInfoByCompanyId(companyId));
    }
    // 其实这里可以用异步的方式, 不过用了前端异步
    @ApiOperation(value = "获取公司的几个hr信息",
            notes = "公司详情页面调用")
    @GetMapping("/company/{companyId}/hrs")
    public Msg getCompanyBitHrs(@PathVariable Long companyId) {
        return Msg.success().add("items", companyService.getCompanyHrs(companyId));
    }

    @ApiOperation(value = "获取公司的position信息",
            notes = "公司详情页面调用, 通过limie判断是否获取少量的这个列表")
    @GetMapping("/company/{companyId}/positions")
    public Msg getCompanyBitsPositions(@PathVariable Long companyId,@RequestParam(value = "page", defaultValue = "1") int page,@RequestParam(value = "limit", defaultValue = "false") boolean limit) {
        return Msg.success().add("pageInfo", companyService.getCompanyPositions(companyId, limit, page));
    }

    @ApiOperation(value = "获取公司列表", notes = "公司列表页面调用")
    @GetMapping("/companys")
    public Msg getCompanys(@RequestParam(defaultValue = "1") int page) {
        return Msg.success().add("pageInfo", companyService.getCompanysList(page));
    }

    @ApiOperation(value = "获取公司详情信息",
            notes = "职位详情列表调用, 和上面的方法大同小异, 只不过多获取了一个companyId")
    @GetMapping("/company/position/{positionId}")
    public Msg getCompanyDetailInfoByPositionId(@PathVariable("positionId") Long positionId) {
        return Msg.success().add("detailInfo", companyService.getCompanyDetailInfoByPositionId(positionId));
    }

    @ApiOperation(value="更新/保存当前hr的company信息")
    @PostMapping("/hr/company")
    public Msg saveOrUpdateHrInfo(@RequestBody @Validated Company company){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        companyService.saveOrUpdateCompany(account.getUserId(), company);
        return Msg.success();
    }
}
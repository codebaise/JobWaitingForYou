package com.home.closematch.controller;

import com.home.closematch.entity.Account;
import com.home.closematch.entity.Humanresoucres;
import com.home.closematch.entity.Position;
import com.home.closematch.entity.SeekerDeliver;
import com.home.closematch.entity.vo.CheckDeliverVo;
import com.home.closematch.pojo.Msg;
import com.home.closematch.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Hr相关操作")
@RestController
public class HrController {

    @Autowired
    private HumanresoucresService humanresoucresService;
    @Autowired
    private JobSeekerService jobSeekerService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private SeekerDeliverService seekerDeliverService;

    @ApiOperation(value="获取当前id的hr所发布的所有职位",
            notes = "管理员行为")
    @GetMapping("/hr/{hrId}/publishPosition")
    public Msg getHrPublishPositions(@PathVariable("hrId")Long hrId){
        return Msg.success("success").add("items", positionService.getPublishPosition(hrId));
    }

    @ApiOperation(value="获取当前hr的信息")
    @GetMapping("/hr")
    public Msg getHrInfoWithCompany(){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Msg.success().add("detailInfo", humanresoucresService.getHrInfoWithCompany(account));
    }


    @ApiOperation(value="更新/保存当前hr的信息")
    @PostMapping("/hr")
    public Msg saveOrUpdateHrInfo(@RequestBody @Validated Humanresoucres humanresoucres){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        humanresoucres.setId(account.getUserId());
        humanresoucresService.saveOrUpdate(humanresoucres);
        return Msg.success();
    }

    @ApiOperation(value="获取当前hr自己发布的所有职位",
            notes = "和上面的方法功能一样, 只不过这里是hr自己获取, 优化Url")
    @GetMapping("/hr/positions")
    public Msg getHrPublishPosition(){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Msg.success().add("items", positionService.getPublishPosition(account.getUserId()));
    }

    @ApiOperation(value="hr去改变该职位的启用状态",
            notes = "hr行为")
    @PutMapping("/hr/position/{positionId}")
    public Msg updatePositionUseStatus(@PathVariable("positionId")Long positionId){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        positionService.changePositionUseStatus(account.getUserId(), positionId);
        return Msg.success("修改成功");
    }

    @ApiOperation(value="添加/修改hr发布的职位信息",
            notes = "hr行为")
    @PostMapping("/hr/position")
    public Msg saveOrUpdatePosition(@RequestBody @Validated Position position) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        humanresoucresService.checkHrInfoEnrolCondition(account.getUserId());
        positionService.saveOrUpdatePosition(account.getUserId(), position);
        return Msg.success();
    }

    /**
     * 获取所发布的所有的职位的应聘者
     * @return Msg 中包含的是一个DTO
     *  仅仅包含相应的教育信息, 当前状态: 应届 / 求职
     *  应聘的职位
     *  需要查详细seeker的详细信息再pull
     */
    @ApiOperation(value="hr获取所发布的所有的职位的应聘者",
            notes = "hr行为")
    @GetMapping("/hr/positions/applicants")
    public Msg getApplicantsWithPosition() {
        // 获取accountId 并验证Account
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Msg.success().add("items", positionService.getApplicantsWithPosition(account.getUserId()));
    }

    @ApiOperation(value="审批求职申请",
            notes = "hr行为")
    @PutMapping("/hr/position/checkDeliver")
    public Msg checkDeliver(@RequestBody @Validated CheckDeliverVo checkDeliverVo){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        seekerDeliverService.checkDeliver(account.getUserId(), checkDeliverVo);
        return Msg.success();
    }

    @ApiOperation(value = "获取投递者基本信息",
            notes = "hr行为, 获取当前投递者的详细信息")
    @GetMapping("/hr/position/deliver/{deliverId}")
    public Msg getSeekerDetailInfo(@PathVariable("deliverId") Long deliverId){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Msg.success().add("detailData", jobSeekerService.hrGetSeekerDetailInfo(account.getUserId(), deliverId));
    }

    @ApiOperation(value = "获取求职者的工作经历",
            notes = "根据相应的deliverId 查询相应的deliver记录, " +
                    "根据deliver的seekerId 查询就职经历" +
                    "为了与上面的方法解耦")
    @GetMapping("/hr/position/deliver/{deliverId}/workExperience")
    public Msg hrGetSeekerWorkExperience(@PathVariable("deliverId") Long deliverId){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Msg.success().add("items", jobSeekerService.hrGetSeekerWorkExperience(account.getUserId(), deliverId));
    }

    @ApiOperation(value = "获取求职者的工作经历的历史评价",
            notes = "根据deliverId 获取 positionId和seekerId, 再到相应表中查找评价")
    @GetMapping("/hr/position/deliver/{deliverId}/workExperience/comment")
    public Msg hrGetWorkExperienceComment(@PathVariable("deliverId") Long deliverId) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SeekerDeliver seekerDeliver = jobSeekerService.checkSeekerRelationOfHr(account.getUserId(), deliverId);
        return Msg.success().add("items", positionService.getPositionComments(seekerDeliver.getSeekId(), seekerDeliver.getPositionId()));
    }
}

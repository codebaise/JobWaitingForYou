package com.home.closematch.controller;

import com.home.closematch.entity.Account;
import com.home.closematch.entity.JobSeeker;
import com.home.closematch.entity.SeekerExperience;
import com.home.closematch.entity.SeekerSchool;
import com.home.closematch.entity.vo.AdvantageVo;
import com.home.closematch.entity.vo.SeekerBaseInfoVo;
import com.home.closematch.entity.vo.SeekerExpectVo;
import com.home.closematch.pojo.Msg;
import com.home.closematch.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "求职者相关")
@RestController
public class SeekerAboutController {

    @Autowired
    private JobSeekerService jobSeekerService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private SeekerSchoolService seekerSchoolService;
    @Autowired
    private SeekerExperienceService seekerExperienceService;
    @Autowired
    private SeekerDeliverService seekerDeliverService;

    // admin行为
    @ApiOperation(value = "求职者工作投递记录", notes = "1377210407541915649")
    @GetMapping("/seeker/{userId}/delivers")
    public Msg getSeekerPositions(@PathVariable("userId") Long userId) {
        return Msg.success("success").add("items", jobSeekerService.getSeekerDeliverWithPosition(userId));
    }

    @ApiOperation(value = "求职者工作经历", notes = "admin, 根据seekerId获取seeker的工作经历")
    @GetMapping("/seeker/{userId}/workExperiences")
    public Msg getSeekerWorkExperience(@PathVariable("userId") Long userId) {
        return Msg.success("success").add("items", jobSeekerService.getSeekerWorkExperiences(userId));
    }

    @ApiOperation(value = "获取seeker的职位评价",
            notes = "admin行为, 获取当前seeker的职位的评价")
    @GetMapping("/seeker/{userId}/positions")
    public Msg getPositionComment(@PathVariable("userId") Long userId, @RequestParam Long positionId) {
        return Msg.success("success").add("items", positionService.getPositionComments(userId, positionId));
    }

// seeker自己的行为
    @ApiOperation(value = "seeker获取个人信息",
            notes = "seeker获取自己的信息")
    @GetMapping("/seeker")
    public Msg getSeekerDetailInfo() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        JobSeeker jobSeeker = jobSeekerService.getById(account.getUserId());
        return Msg.success("success").add("seeker", jobSeeker);
    }

    @ApiOperation(value = "添加/更新个人基本信息",
            notes = "seeker行为, 对自己的信息进行添加/修改")
    @PostMapping("/seeker")
    public Msg savePersonalDetailInfo(@RequestBody @Validated SeekerBaseInfoVo seekerBaseInfoVo) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        jobSeekerService.saveOrUpdateSeekerInfo(account.getUserId(), seekerBaseInfoVo);
        return Msg.success("Ok");
    }

    @ApiOperation(value = "获取教育经历信息")
    @GetMapping("/seeker/school")
    public Msg getSeekerSchoolInfo() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Msg.success("success").add("schools", seekerSchoolService.getSeekerSchoolsBySeekerId(account.getUserId()));
    }

    @ApiOperation(value = "新增seeker的教学经历")
    @PostMapping("/seeker/school")
    public Msg addSeekerSchoolInfo(@RequestBody @Validated SeekerSchool seekerSchool) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        seekerSchoolService.addSeekerSchoolInfo(account.getUserId(), seekerSchool);
        return Msg.success("success");
    }

    @ApiOperation(value = "drop seeker的教学经历")
    @DeleteMapping("/seeker/school/{schoolId}")
    public Msg dropSeekerSchoolInfo(@PathVariable Long schoolId) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        seekerSchoolService.dropSeekerSchoolInfo(account.getUserId(), schoolId);
        return Msg.success("success");
    }

    @ApiOperation(value = "修改 seeker的教学经历")
    @PutMapping("/seeker/school/{schoolId}")
    public Msg updateSeekerSchoolInfo(@PathVariable Long schoolId, @RequestBody @Validated SeekerSchool seekerSchool) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        seekerSchoolService.updateSeekerSchoolInfo(account.getUserId(), schoolId, seekerSchool);
        return Msg.success("success");
    }

    @ApiOperation(value = "获得求职经历信息s",
            notes = "根据seekerId, 获得自己的求职经历信息")
    @GetMapping("/seeker/experiences")
    public Msg getSeekerExperienceInfo() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Msg.success("success").add("experiences", seekerExperienceService.getSeekerExperienceInfoBySeekerId(account.getUserId()));
    }

    @ApiOperation(value = "新增工作经验",
            notes = "seeker新增工作经验")
    @PostMapping("/seeker/experience")
    public Msg addSeekerExperience(@RequestBody @Validated SeekerExperience seekerExperience) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        seekerExperienceService.addSeekerExperience(account.getUserId(), seekerExperience);
        return Msg.success("success");
    }

    @ApiOperation(value = "对求职经历进行添加/修改", notes = "虽然只能修改自己的求职经历, 但是也可能添加了多条")
    @PutMapping("/seeker/experience/{experienceId}")
    public Msg updateSeekerExperienceInfo(@PathVariable("experienceId") Long experienceId, @RequestBody SeekerExperience seekerExperience) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        seekerExperienceService.updateSeekerExperienceInfo(account.getUserId(), experienceId, seekerExperience);
        return Msg.success("Ok");
    }

    @ApiOperation(value = "投递申请", notes = "seeker投递positionId对应的职位")
    @PostMapping("/seeker/deliver/{positionId}")
    public Msg deliverResume(@PathVariable("positionId") Long positionId) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        seekerDeliverService.sendDeliver(account.getUserId(), positionId);
        return Msg.success();
    }

    @ApiOperation("单独填写Advantage字段")
    @PostMapping("/seeker/advantage")
    public Msg editSeekerAdvantage(@RequestBody @Validated AdvantageVo advantageVo) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        jobSeekerService.editAdvantage(account.getUserId(), advantageVo);
        return Msg.success();
    }

    @ApiOperation("单独填写Advantage字段")
    @PostMapping("/seeker/expect")
    public Msg editExpect(@RequestBody @Validated SeekerExpectVo seekerExpectVo){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        jobSeekerService.editExpect(account.getUserId(), seekerExpectVo);
        return Msg.success();
    }
}

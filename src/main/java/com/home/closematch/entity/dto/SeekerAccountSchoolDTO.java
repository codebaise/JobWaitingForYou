package com.home.closematch.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.home.closematch.entity.JobSeeker;
import com.home.closematch.entity.SeekerSchool;
import lombok.Data;

import java.util.Date;

/**
 * admin:
 *  最初版本
 *  一次性获取:
 *      seeker账户信息
 *      seeker个人信息
 *      seeker学校信息
 */
@Data
public class SeekerAccountSchoolDTO {
    // account
    private Long accountId;
    private String username;
    // seeker
    private Long seekerId;
    private String name;
    private Integer age;
    private Integer userIdentity; // 用户身份 用户身份 0 -> 应届生  || 1 -> 职场人士
    /**
     * 当前状态
     *   职场人
     *    离职找工作
     *    在职找工作
     *    在职看机会
     *    暂时不找工作
     *   应届生
     *    不用选
     */
    private Integer currentStatus; // 当前用户状态
    private String expectPosition;
    private String expectCity;
    private Integer isDelete;
    // ss.school_name, ss.education,  ss.start_time, ss.end_time
    private String schoolName;
    private Integer education;
    private String domain;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date endTime;

    public void setSeeker(JobSeeker jobSeeker){
//        this.seekerId = jobSeeker.getId();
        this.name = jobSeeker.getName();
        this.age = jobSeeker.getAge();
        this.userIdentity = jobSeeker.getUserIdentity();
        this.currentStatus = jobSeeker.getCurrentStatus();
    }

    public void setSchool(SeekerSchool seekerSchool){
        this.schoolName = seekerSchool.getSchoolName();
        this.education = seekerSchool.getEducation();
        this.domain = seekerSchool.getDomain();
    }
}

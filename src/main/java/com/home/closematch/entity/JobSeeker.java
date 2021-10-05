package com.home.closematch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * null
 * @TableName cm_job_seeker
 */
@TableName(value ="cm_job_seeker")
@Data
@NoArgsConstructor
public class JobSeeker extends BaseEntity implements Serializable {
    /**
     * 
     */
    @TableId(type= IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonIgnore
    private Long id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private Integer age;

    /**
     * 用户身份 0 -> 应届生  || 1 -> 职场人士 || 2 -> 已经入职
     */
    private Integer userIdentity;

    /**
     * 当前状态
  职场人
   离职找工作
   在职找工作
   在职看机会
   暂时不找工作
  应届生 不用选
     */
    private Integer currentStatus;

    /**
     * 期望职位
     */
    private String expectPosition;

    /**
     * 期望城市
     */
    private String expectCity;

    /**
     * 个人优势
     */
    private String advantage;

    /**
     * 
     */
    private Integer minExpectSalary;

    /**
     * 
     */
    private Integer maxExpectSalary;

    @TableField(exist = false)
    private String email;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public JobSeeker(String name, Integer age, Integer userIdentity, Integer currentStatus, String expectPosition, String expectCity, String advantage, Integer minExpectSalary, Integer maxExpectSalary) {
        this.name = name;
        this.age = age;
        this.userIdentity = userIdentity;
        this.currentStatus = currentStatus;
        this.expectPosition = expectPosition;
        this.expectCity = expectCity;
        this.advantage = advantage;
        this.minExpectSalary = minExpectSalary;
        this.maxExpectSalary = maxExpectSalary;
    }
}
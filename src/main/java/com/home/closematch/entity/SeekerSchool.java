package com.home.closematch.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * null
 * @TableName cm_seeker_school
 */
@TableName(value ="cm_seeker_school")
@Data
public class SeekerSchool extends BaseEntity implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 
     */
    @NotEmpty(message = "请填写学校名")
    private String schoolName;

    /**
     * 
     */
    private String domain;

    /**
     * 
     */
    @NotNull(message = "请填写学历")
    private Integer education;

    /**
     * 
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @NotNull(message = "请填写开始时间")
    private Date startTime;

    /**
     * 
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @NotNull(message = "请填写毕业时间")
    private Date endTime;

    /**
     * 
     */
    @JsonIgnore
    private Long userId;

    private String description;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
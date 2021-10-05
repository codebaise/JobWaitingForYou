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
 * @TableName cm_seeker_experience
 */
@TableName(value ="cm_seeker_experience")
@Data
public class SeekerExperience extends BaseEntity implements Serializable {
    /**
     * 
     */
    @TableId
//    @JsonIgnore
    private Long id;

    /**
     * 
     */
    @NotEmpty(message = "请填写公司名")
    private String companyName;

    /**
     * 
     */
    @NotEmpty(message = "请填写职位名称")
    private String position;

    /**
     * 
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    @NotNull(message = "请填写就职时间")
    private Date startTime;

    /**
     * 
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    @NotNull(message = "请填写离职时间")
    private Date endTime;

    /**
     * 
     */
    private String description;

    /**
     * 
     */
//    @JsonIgnore
    private Long userId;

    /**
     * 
     */
//    @JsonIgnore
    private Long positionId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
package com.home.closematch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * null
 * @TableName cm_position
 */
@TableName(value ="cm_position")
@Data
public class Position extends BaseEntity implements Serializable {
    /**
     * 
     */
    @TableId(type= IdType.ASSIGN_ID)
//    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 招聘类型
     */
    @NotNull(message="招聘类型不能为空")
    @Min(0)
    private Integer type;

    /**
     * 职位名称
     */
    // position_name
    @NotEmpty(message="职位名称不能为空")
    private String positionName;

    /**
     * 城市名
     */
    @NotEmpty(message="工作城市不能为空")
    private String workCity;

    /**
     * 
     */
    @NotNull(message="工作时长不能为空")
    @Min(0)
    private Integer workHours;

    /**
     * min_experience, 用数字存储, 到时候直接大于这个数字的就行
     */
    @NotNull(message="学历限制不能为null")
    @Min(0)
    private Integer minExperience;

    /**
     * 
     */
    @NotNull(message="工作经验限制不能为null")
    @Min(0)
    private Integer minEducation;

    /**
     * 
     */
    @NotNull(message="最低薪资不能为null")
    @Min(value = 0, message = "minSalaryError")
    private Integer minSalary;

    /**
     * 
     */
    private String description;

    /**
     * 职位类型: java等
     */
    @NotEmpty(message="职位类型不能为空")
    private String positionType;

    /**
     当前发布的职位的审核状态
     0: 等待审核 1: 不可用, 2可用
     3: 启用 4: 不启用
      */
    private Integer status;


    /**
     * 
     */
    @JsonIgnore
//    @JsonSerialize(using = ToStringSerializer.class)
    private Long hrId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    {
        status = 0;
    }
}
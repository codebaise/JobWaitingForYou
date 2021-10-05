package com.home.closematch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * null
 * @TableName cm_company
 */
@TableName(value ="cm_company")
@Data
public class Company extends BaseEntity implements Serializable {
    /**
     * 
     */
    @TableId(type= IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 请填写与营业执照名称/劳动合同/公司发票抬头一致的公司全称, 不符合则不予通过
     */
    @NotEmpty(message = "请填写与营业执照名称/劳动合同/公司发票抬头一致的公司全称")
    private String name;

    /**
     * 
     */
    @NotEmpty(message = "请输入公司简称")
    private String simpleName;

    /**
     * 直接列出来一系列domain
     */
    @NotEmpty(message = "请输入主营范围")
    private String domain;

    /**
     * 
     */
    private String description;

    /**
     * 直接存储 eg: 0-20人
     */
    @NotEmpty(message = "请输入公司规模")
    private String scale;

    private Integer auditStatus;
    {
        auditStatus = 0;
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
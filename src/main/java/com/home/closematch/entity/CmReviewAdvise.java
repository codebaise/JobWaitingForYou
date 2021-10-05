package com.home.closematch.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * 对公司的审核评议表
 * @TableName cm_review_advise
 */
@TableName(value ="cm_review_advise")
@Data
public class CmReviewAdvise extends BaseEntity implements Serializable {
    /**
     * 
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 0 seeker 1 hr 2 company
     */
    private Integer reviewType;

    /**
     * 
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long reviewObjId;

    /**
     * 
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long reviewAdminId;

    /**
     * 
     */
    private String reviewAdvise;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
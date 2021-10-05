package com.home.closematch.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * null
 * @TableName cm_seeker_experience
 */
@TableName(value ="cm_seeker_experience")
@Data
public class CmSeekerExperience extends BaseEntity implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 
     */
    private String companyName;

    /**
     * 
     */
    private String position;

    /**
     * 
     */
    private Date startTime;

    /**
     * 
     */
    private Date endTime;

    /**
     * 
     */
    private String description;

    /**
     * 
     */
    private Long userId;

    /**
     * 
     */
    private Long positionId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
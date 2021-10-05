package com.home.closematch.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * null
 * @TableName cm_hr2staff_comment
 */
@TableName(value ="cm_hr2staff_comment")
@Data
public class Hr2staffComment extends BaseEntity implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 
     */
    private Long hrId;

    /**
     * 
     */
    private Long staffId;

    /**
     * 
     */
    private String comment;

    /**
     * 
     */
    private Long positionId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
package com.home.closematch.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * null
 * @TableName cm_seeker_deliver
 */
@TableName(value ="cm_seeker_deliver")
@Data
public class SeekerDeliver extends BaseEntity implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 
     */
    private Long seekId;

    /**
     * 
     */
    private Long positionId;

    /**
     * 0 未被处理 1  pass 2允许面试
     */
    private Integer status;
    {
        status = 0;
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
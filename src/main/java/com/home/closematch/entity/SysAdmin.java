package com.home.closematch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * null
 * @TableName cm_sys_admin
 */
@TableName(value ="cm_sys_admin")
@Data
public class SysAdmin extends BaseEntity implements Serializable {
    /**
     * 
     */
    @TableId(type= IdType.ASSIGN_ID)
    private String username;

    /**
     * 
     */
    private String password;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
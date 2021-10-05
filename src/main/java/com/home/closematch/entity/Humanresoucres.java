package com.home.closematch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotEmpty;

/**
 * null
 * @TableName cm_humanresoucres
 */
@TableName(value ="cm_humanresoucres")
@Data
@EqualsAndHashCode(callSuper = false)
public class Humanresoucres extends BaseEntity implements Serializable {
    /**
     * 
     */
    @TableId(type= IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 
     */
    @NotEmpty(message = "请填写姓名")
    private String name;

    /**
     * 
     */
    @NotEmpty(message = "请填写职位信息")
    private String curPosition;

    /**
     * 
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long companyId;

    /**
     * 与账户状态区分, 账户状态true可以登录
     * 但是这个状态未false 则不能发布职位等
     * 当前的状态, 如果是0
     * 说明发布的职位什么都不会被显示, 1则说明审核通过
     * 只有当公司申请通过以后才被认定为审核通过
     */
    private Integer status;

    {
        status = 0;
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
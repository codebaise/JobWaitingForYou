package com.home.closematch.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * 私信信息表,包含了所有用户的私信信息
 * @TableName cm_message
 */
@TableName(value ="cm_message")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message extends BaseEntity implements Serializable {
    /**
     * 主键
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 对应通知消息的id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long notifyId;

    /**
     * 发送者用户ID, 这里的send_id 和 recv_id 主要为了在私信中进行区分
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long senderId;

    /**
     * 接受者用户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long reciverId;

    /**
     * 消息内容,最长长度不允许超过1000
     */
    private String title;

    /**
     * 
     */
    private String content;

    /**
     * 
     */
    private Integer isTimed;

    /**
     * 
     */
    private Date date;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
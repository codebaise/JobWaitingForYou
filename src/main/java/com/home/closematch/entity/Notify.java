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
import lombok.ToString;

/**
 * 用户通知表,包含了所有用户的消息
 * @TableName cm_notify
 */
@TableName(value ="cm_notify")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Notify extends BaseEntity implements Serializable {
    /**
     * 主键
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 发送者账户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long senderId;

    /**
     * 接受者账户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long reciverId;

    /**
     * 消息类型:announcement公告/remind提醒/message私信
     */
    private String type;

    /**
     * 是否已读,0未读,1已读
     */
    private Boolean isRead;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
package com.home.closematch.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 通知信息的Vo
 * 管理员发来的通知信息VO
 */
@Data
public class NotifyMessageVo {
    // sendId无用, 自定评判
    // receiveId 后期指定人发送的时候, 需要提供
    private Long senderId; // sendid 则直接从各种地方获取即可
    private Long receiveId; // receive id 应该提供

    @NotNull
    @NotEmpty
    private String type;         // 消息类型:announcement公告/remind提醒/message私信

    @NotEmpty
    private String title;

    @NotNull
    private boolean isTimed; // 是否为定时任务

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date date;

    @NotEmpty
    private String content;
//    private String desc; // 当前message的vo

    {
        senderId = 1L;
        receiveId = 1L;
    }

}

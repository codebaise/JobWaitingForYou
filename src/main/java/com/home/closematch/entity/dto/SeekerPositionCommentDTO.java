package com.home.closematch.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 获取当前seeker
 * 的 相应的工作经历
 * 的 hr对其的评价
 */
@Data
public class SeekerPositionCommentDTO {
    private String hrPosition;
    private String comment;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date createTime;
}

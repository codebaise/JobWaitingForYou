package com.home.closematch.entity.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * ...重复了, 直接使用Experience一样
 */
@Data
public class SeekerExperienceVo {
    @NotEmpty(message = "请填写公司名")
    private String companyName;
    @NotEmpty(message = "请填写职位名称")
    private String position;
    @NotEmpty(message = "请填写就职时间")
    private Date startTime;
    @NotEmpty(message = "请填写离职时间")
    private Date endTime;

    private String description;
}

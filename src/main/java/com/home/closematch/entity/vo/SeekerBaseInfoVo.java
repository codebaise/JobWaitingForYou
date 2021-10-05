package com.home.closematch.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class SeekerBaseInfoVo {
    @NotEmpty(message = "请输入姓名")
    private String name;
    @NotNull(message = "请勿随意修改")
    @Min(0)
    @Max(1)
    private Integer userIdentity;
    @NotNull(message = "请勿随意修改")
    @Min(0)
    @Max(5)
    private Integer currentStatus;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @NotNull(message = "请填写日期")
    private Date birthday;
}

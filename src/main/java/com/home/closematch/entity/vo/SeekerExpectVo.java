package com.home.closematch.entity.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class SeekerExpectVo {
    @NotEmpty(message = "请填写职位名称")
    private String expectPosition;
    @NotEmpty(message = "请填写期望城市")
    private String expectCity;
    @NotNull(message = "请填写期望薪资, K为单位")
    private Integer minExpectSalary;
}

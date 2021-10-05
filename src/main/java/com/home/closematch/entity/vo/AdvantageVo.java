package com.home.closematch.entity.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AdvantageVo {
    @NotEmpty(message = "请输入相应内容哦~")
    private String advantage;
}

package com.home.closematch.entity.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * 专门用来验证email的vo
 * 被RegisterVo继承
 */
@Data
public class EmailVo {
    @NotEmpty(message = "邮箱不能为空")
    @Pattern(regexp = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", message = "请输入正确的邮箱")
    private String email;
}

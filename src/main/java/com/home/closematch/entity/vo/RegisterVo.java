package com.home.closematch.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class RegisterVo extends EmailVo{
    @NotEmpty
    @Length(min = 6, max = 6, message = "请输入正确的验证码")
    private String verifyCode;

    @NotEmpty
    @Length(min = 6, max = 20, message = "密码长度为6到20位")
    private String password;

    @NotNull
    @Min(value = 0, message = "请勿修改参数")
    @Max(value = 1, message = "请勿修改参数")
    private Integer registerType;
}

package com.home.closematch.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * getUserInfo方法返回
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserBaseInfoVo {
    private String name;
    private Integer userType;
    private List<String> roles;
    private Integer userInfoFillLevel;
    private String homeLink;
}

package com.home.closematch.entity.vo;

import lombok.Data;

/**
 * 公司详情部分return
 * 但是在只为详情中有近乎相同的所有字段 , 则被复用
 */
@Data
public class CompanyDetailInfoVo {
    // companyId 在职位详情显示中
    // 右侧一个部分需要用这个id进行跳转
    private Long companyId;
    private String companyName;
    private String simpleName;
    private String domain;
    private String description;
    private String scale;
    //positions
//    private List<PositionHrVo> positionHrVos;
    private Integer positionCount;
    // hr
//    private List<Humanresoucres> humanresoucresList;
    private Integer hrCount;
}

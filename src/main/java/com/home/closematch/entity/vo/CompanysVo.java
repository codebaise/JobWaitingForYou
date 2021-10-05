package com.home.closematch.entity.vo;

import lombok.Data;

/**
 * 公司列表所需信息
 */
@Data
public class CompanysVo {
    private Long companyId;
    private String companyName;
    private String scale;
    private String domain;

    private Long positionId;
    private String positionName;
    private Integer minSalary;
}

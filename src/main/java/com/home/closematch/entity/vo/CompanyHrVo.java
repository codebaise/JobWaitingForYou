package com.home.closematch.entity.vo;

import lombok.Data;

/**
 * 公司的hr相关的少量信息Vo
 * 公司详情列表右侧的几个hr信息
 */
@Data
public class CompanyHrVo {
    private String hrName;
    private String curPosition;

    private int positionCount;

}

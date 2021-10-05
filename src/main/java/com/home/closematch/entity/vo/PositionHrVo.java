package com.home.closematch.entity.vo;

import com.home.closematch.entity.Humanresoucres;
import com.home.closematch.entity.Position;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公司列表中
 * 左下方
 * 显示的职位信息和相应的hr的信息
 * 被 PositionDetailInfoVo 继承
 */
@Data
@NoArgsConstructor
public class PositionHrVo {
    private String hrName;
    private String hrPosition;

    private Long positionId;
    private String positionName;
    private Integer minSalary;
    private String workCity;
    private Integer minEducation;
    private Integer minExperience;

    public PositionHrVo(Humanresoucres humanresoucres, Position position) {
        this.hrName = humanresoucres.getName();
        this.hrPosition = humanresoucres.getCurPosition();
        this.positionId = position.getId();
        this.positionName = position.getPositionName();
        this.minSalary = position.getMinSalary();
        this.workCity = position.getWorkCity();
        this.minEducation = position.getMinEducation();
        this.minExperience = position.getMinExperience();
    }
}

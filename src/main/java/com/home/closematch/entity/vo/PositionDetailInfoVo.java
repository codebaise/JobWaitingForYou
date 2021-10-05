package com.home.closematch.entity.vo;

import com.home.closematch.entity.Humanresoucres;
import com.home.closematch.entity.Position;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 每个职位详情部分需要的信息
 */
@Data
@NoArgsConstructor
public class PositionDetailInfoVo extends PositionHrVo{
    private Integer workHours;
    private String description;

    public PositionDetailInfoVo(Humanresoucres humanresoucres, Position position){
        super(humanresoucres, position);
        this.workHours = position.getWorkHours();
        this.description = position.getDescription();
    }
}

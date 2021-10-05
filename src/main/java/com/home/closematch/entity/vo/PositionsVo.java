package com.home.closematch.entity.vo;

import com.home.closematch.entity.Company;
import com.home.closematch.entity.Humanresoucres;
import com.home.closematch.entity.Position;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PositionsVo extends PositionHrVo {


    // company
    private String companyName;
    private String companyScale;
    private String companyDomain;

    public PositionsVo(Humanresoucres humanresoucres, Position position, Company company) {
        super(humanresoucres, position);
        this.companyName = company.getName();
        this.companyScale = company.getScale();
        this.companyDomain = company.getDomain();
    }

}

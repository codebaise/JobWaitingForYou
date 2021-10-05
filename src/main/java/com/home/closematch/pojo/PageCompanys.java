package com.home.closematch.pojo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.home.closematch.entity.Company;
import com.home.closematch.entity.vo.CompanysVo;

import java.util.List;

public class PageCompanys extends Page<Company> {

    private List<CompanysVo> companysVos;

    public PageCompanys(long current, long size) {
        super(current, size, 0);
    }
    public List<CompanysVo> getCompanysVos() {
        return companysVos;
    }

    public void setCompanysVos(List<CompanysVo> companysVos) {
        this.companysVos = companysVos;
    }
}

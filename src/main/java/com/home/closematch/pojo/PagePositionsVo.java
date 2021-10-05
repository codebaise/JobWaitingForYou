package com.home.closematch.pojo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.home.closematch.entity.Position;
import com.home.closematch.entity.vo.PositionsVo;

import java.util.List;

public class PagePositionsVo extends Page<Position> {

    private List<PositionsVo> positionsVos;

    public PagePositionsVo(long current, long size) {
        super(current, size, 0);
    }

    public List<PositionsVo> getPositionsVos() {
        return positionsVos;
    }

    public void setPositionsVos(List<PositionsVo> positionsVos) {
        this.positionsVos = positionsVos;
    }

}

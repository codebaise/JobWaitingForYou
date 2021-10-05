package com.home.closematch.pojo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.home.closematch.entity.Position;
import com.home.closematch.entity.vo.PositionHrVo;

import java.util.List;

public class PagePositionHr extends Page<Position> {

    private List<PositionHrVo> PositionHrVos;

    public PagePositionHr(long current, long size) {
        super(current, size, 0);
    }

    public List<PositionHrVo> getPositionHrVos() {
        return PositionHrVos;
    }

    public void setPositionHrVos(List<PositionHrVo> PositionHrVos) {
        this.PositionHrVos = PositionHrVos;
    }

}

package com.home.closematch.controller;

import com.home.closematch.entity.Position;
import com.home.closematch.exception.ServiceErrorException;
import com.home.closematch.pojo.Msg;
import com.home.closematch.service.PositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "职位相关")
@RestController
public class PositionController {
    @Autowired
    private PositionService positionService;

    @ApiOperation(value = "获取职位列表",
            notes = "分页获取所有的职位列表")
    @GetMapping("/positions")
    public Msg getPositions(@RequestParam int page) {
        return Msg.success().add("pageInfo", positionService.getPositions(page));
    }

    @ApiOperation(value = "获取职位详情信息",
            notes = "根据positionId获取相应的位置的详情信息" +
                    "PositionDetailInfoVo 包含hr信息和职位信息" +
                    "公司信息用companyController相应方法获取")
    @GetMapping("/position/{positionId}")
    public Msg getPositionDetailInfo(@PathVariable("positionId") Long positionId) {
        return Msg.success().add("detailInfo", positionService.getPositionDetailInfoById(positionId));
    }
}

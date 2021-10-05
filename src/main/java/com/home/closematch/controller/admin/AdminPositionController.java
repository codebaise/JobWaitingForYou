package com.home.closematch.controller.admin;

import com.home.closematch.pojo.Msg;
import com.home.closematch.service.PositionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "AdminPosition", tags = "后台管理")
@RestController
public class AdminPositionController {

    @Autowired
    private PositionService positionService;

    @PutMapping("/backStageManagement/position/{positionId}")
    public Msg changePositionStatus(@PathVariable("positionId") Long positionId, @RequestParam("status") Integer status){
        positionService.changePositionStatusByPositionId(positionId, status);
        return Msg.success("success");
    }
}

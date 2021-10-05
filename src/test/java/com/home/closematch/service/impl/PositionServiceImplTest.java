package com.home.closematch.service.impl;

import com.home.closematch.pojo.PagePositionsVo;
import com.home.closematch.service.PositionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PositionServiceImplTest {
    @Autowired
    private PositionService positionService;

    @Test
    void getPositions() {
        PagePositionsVo positions = positionService.getPositions(1);
        System.out.println();
    }
}
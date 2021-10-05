package com.home.closematch.entity;

import com.home.closematch.entity.vo.NotifyMessageVo;
import com.home.closematch.service.NotifyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MessageServiceTest {
    @Autowired
    private NotifyService notifyService;

    @Test
    public void sendMessageTest(){
        NotifyMessageVo notifyMessageVo = new NotifyMessageVo();
        notifyMessageVo.setContent("testContent");
        notifyMessageVo.setSenderId(1L);
        notifyMessageVo.setReceiveId(2L);
        notifyMessageVo.setType("announcement");
        notifyService.sendGlobalMessage(notifyMessageVo);
    }
}

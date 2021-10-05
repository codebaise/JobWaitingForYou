package com.home.closematch.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.home.closematch.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.home.closematch.entity.dto.BaseMessageDTO;

/**
 *
 */
public interface MessageService extends IService<Message> {
    IPage<BaseMessageDTO> getGlobalMessage(Integer page);

    IPage<BaseMessageDTO> getUserSelfMessage(Integer page, Long userId, Integer userType) ;

    int getMessageNotReadCount(Long userId, Integer userType);
}

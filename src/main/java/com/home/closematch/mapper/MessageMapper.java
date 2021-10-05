package com.home.closematch.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.home.closematch.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.home.closematch.entity.dto.BaseMessageDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Entity com.home.closematch.entity.Message
 */
@Repository
public interface MessageMapper extends BaseMapper<Message> {
    IPage<BaseMessageDTO> getGlobalMessage(Page<BaseMessageDTO> page);

    IPage<BaseMessageDTO> getUserSelfMessageByAccountId(Page<BaseMessageDTO> page, Long accountId);
}





package com.home.closematch.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.closematch.entity.Account;
import com.home.closematch.entity.Message;
import com.home.closematch.entity.Notify;
import com.home.closematch.entity.dto.BaseMessageDTO;
import com.home.closematch.exception.ServiceErrorException;
import com.home.closematch.service.AccountService;
import com.home.closematch.service.MessageService;
import com.home.closematch.mapper.MessageMapper;
import com.home.closematch.service.NotifyService;
import com.home.closematch.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message>
implements MessageService{
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private AccountService accountService;
    @Autowired
    private NotifyService notifyService;

    @Override
    public IPage<BaseMessageDTO> getGlobalMessage(Integer page) {
        // 埋坑: 这里应该获取以下自身id' pass 因为是全局通知, 所以没必要获取id
        return messageMapper.getGlobalMessage(new Page<>(page, CommonUtils.perPageSize));
    }

    @Override
    public IPage<BaseMessageDTO> getUserSelfMessage(Integer page, Long userId, Integer userType) {
        // 根据userId查找用户
        Account account = accountService.getOne(new QueryWrapper<Account>().eq("user_id", userId).eq("user_type", userType));
        // 双重验证用户用户正确性
        if (account == null)
            throw new ServiceErrorException(400, "Account Error");
        // 修改这些获取过的通知状态
        // 获取当前AccountId 对应 的 用户的remind message
        List<Notify> notifies = notifyService.list(new QueryWrapper<Notify>()
                .eq("reciver_id", account.getId())
                .eq("type", "remind"));

        for (Notify next : notifies) {
            next.setIsRead(true);
        }

        notifyService.updateBatchById(notifies);
        return messageMapper.getUserSelfMessageByAccountId(new Page<>(page, CommonUtils.perPageSize), account.getId());
    }

    @Override
    public int getMessageNotReadCount(Long userId, Integer userType) {
        Account one = accountService.getOne(new QueryWrapper<Account>().eq("user_id", userId).eq("user_type", userType));
        return notifyService.count(new QueryWrapper<Notify>().eq("reciver_id", one.getId()).eq("is_read", 0));
    }
}





package com.home.closematch.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.closematch.entity.*;
import com.home.closematch.entity.vo.NotifyMessageVo;
import com.home.closematch.exception.ServiceErrorException;
import com.home.closematch.service.AccountService;
import com.home.closematch.service.JobSeekerService;
import com.home.closematch.service.MessageService;
import com.home.closematch.service.NotifyService;
import com.home.closematch.mapper.NotifyMapper;
import com.home.closematch.utils.NotifyUtils;
import com.home.closematch.utils.SeekerDeliverUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class NotifyServiceImpl extends ServiceImpl<NotifyMapper, Notify>
        implements NotifyService {

    @Autowired
    private NotifyMapper notifyMapper;
    @Autowired
    private MessageService messageService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private JobSeekerService jobSeekerService;

    @Override
    public void sendGlobalMessage(NotifyMessageVo notifyMessageVo) {
        // 埋坑, 日后需要私聊 , 查询notify的时候需要加上一个判断即可
        // 判断 sender id / receive id 为两个id 相互对一个即可, 没必要必须位置也统一
        // 埋坑: 处理私聊
        // 消息类型:announcement公告/remind提醒/message私信
        Notify notify = null;
        if ("message".equals(notifyMessageVo.getType())) {
            QueryWrapper<Notify> notifyQueryWrapper = new QueryWrapper<Notify>()
                    .eq("sender_id", notifyMessageVo.getSenderId())
                    .eq("reciver_id", notifyMessageVo.getReceiveId())
                    .eq("type", notifyMessageVo.getType());
            notify = notifyMapper.selectOne(notifyQueryWrapper);
        }

        if (notify == null) {
            notify = new Notify(null, notifyMessageVo.getSenderId(), notifyMessageVo.getSenderId(), notifyMessageVo.getType(), false);
            notifyMapper.insert(notify);
        }
        // 埋坑, 真正的定时任务, 在这里加入到定时任务中去
        if (notify.getId() != null) {
            Message message = new Message(null, notify.getId(), notifyMessageVo.getSenderId(),
                    notifyMessageVo.getReceiveId(), notifyMessageVo.getTitle(), notifyMessageVo.getContent(),
                    notifyMessageVo.isTimed() ? 1 : 0, notifyMessageVo.getDate());
            messageService.save(message);
        } else {
            throw new ServiceErrorException(400, "Error Message Operation");
        }

    }

    /**
     * 创建一个Notify对象, 拿到它的Id
     */
    private Long createNotify(Long sendId, Long receiverId, String type){
        Notify notify = new Notify();
        notify.setIsRead(NotifyUtils.IS_READ_FALSE);
        notify.setReciverId(receiverId);
        notify.setSenderId(sendId);
        notify.setType(type);
        notifyMapper.insert(notify);
        return notify.getId();
    }

    private void createMessage(String title, String content, Long sendId, Long receiverId, boolean isTiming, Long notifyId){
        Message message = new Message();
        message.setTitle(title);
        message.setContent(content);
        message.setSenderId(sendId);
        message.setReciverId(receiverId);
        message.setIsTimed(isTiming ? 1 : 0);
        message.setNotifyId(notifyId);

        messageService.save(message);
    }
    /**
     * 根据hr对seeker的求职申请审核状态等信息, 构造两条通知
     */
    @Override
    public void sendHrCheckDeliverMessage(SeekerDeliver seekerDeliver, Integer checkValue, String positionName, Long hrId) {
        // 获取hr和seeker的账户用来发message
        Account hrAccount = accountService.getOne(new QueryWrapper<Account>().eq("user_id", hrId));
        Account seekerAccount = accountService.getOne(new QueryWrapper<Account>().eq("user_id", seekerDeliver.getSeekId()));
        // 向seeker 发送一条remind消息
        // 这里有个异常, 如果查不到account时会抛出NullPointException
        if (hrAccount == null || seekerAccount == null)
            throw new ServiceErrorException(400, "该Seeker已不存在");
//        Notify notifyOfSeeker = new Notify();
//        notifyOfSeeker.setIsRead(NotifyUtils.IS_READ_FALSE);
//        notifyOfSeeker.setReciverId(seekerAccount.getId());
//        notifyOfSeeker.setSenderId(hrAccount.getId()); // 或许sendId 应该是管理员
//        notifyOfSeeker.setType(NotifyUtils.TYPE_REMIND_MESSAGE);

        Long notifyOneId = createNotify(hrAccount.getId(), seekerAccount.getId(), NotifyUtils.TYPE_REMIND_MESSAGE);
        // 更新两条数据
        createMessage("系统通知", SeekerDeliverUtils.formatCheckCommentToSeeker(positionName, checkValue),
                hrAccount.getId(), seekerAccount.getId(), false, notifyOneId);
//        Message messageOfSeeker = new Message();
//        messageOfSeeker.setContent(SeekerDeliverUtils.formatCheckCommentToSeeker(positionName, checkValue));
//        messageOfSeeker.setReciverId(seekerAccount.getId());
//        messageOfSeeker.setSenderId(hrAccount.getId());
//        messageOfSeeker.setIsTimed(0);
//        messageOfSeeker.setTitle("系统通知");
//        messageOfSeeker.setNotifyId(notifyOfSeeker.getId());

        // 向hr  发送一条remind消息
        Long notifyTwoId = createNotify(1L, hrAccount.getId(), NotifyUtils.TYPE_REMIND_MESSAGE);

        JobSeeker jobSeeker = jobSeekerService.getById(seekerDeliver.getSeekId());
        createMessage("系统通知", SeekerDeliverUtils.formatCheckCommentToHr(jobSeeker.getName(), positionName, checkValue),
                1L, hrAccount.getId(), false, notifyTwoId);
    }

    @Override
    public void sendSeekerDeliverPositionMessageSuccessMessage(Long seekerId, Long hrId, String positionName) {
        Account hrAccount = accountService.getOne(new QueryWrapper<Account>().eq("user_id", hrId));
        Account seekerAccount = accountService.getOne(new QueryWrapper<Account>().eq("user_id", seekerId));

        Long notifyOneId = createNotify(1L, seekerAccount.getId(), NotifyUtils.TYPE_REMIND_MESSAGE);
        createMessage("你的申请已投递",
                "您申请的 [ " + positionName + " ] 职位已经成功投递, 请等待对方的审批 !!!",
                1L, seekerAccount.getId(), false, notifyOneId);

        JobSeeker seeker = jobSeekerService.getById(seekerId);
        Long notifyTwoId = createNotify(1L, hrAccount.getId(), NotifyUtils.TYPE_REMIND_MESSAGE);
        createMessage("新的求职申请",
                "您收到来自[ " + seeker.getName() + " ] 对 [ " + positionName +" ]的求职申请, 请尽快审批 !!!",
                1L, hrAccount.getId(), false, notifyTwoId);
    }
}





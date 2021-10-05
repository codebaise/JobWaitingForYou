package com.home.closematch.service;

import com.home.closematch.entity.Notify;
import com.baomidou.mybatisplus.extension.service.IService;
import com.home.closematch.entity.SeekerDeliver;
import com.home.closematch.entity.dto.BaseMessageDTO;
import com.home.closematch.entity.vo.NotifyMessageVo;

import java.util.List;

/**
 *
 */
public interface NotifyService extends IService<Notify> {
    void sendGlobalMessage(NotifyMessageVo notifyMessageVo);

    /**
     * 根据hr对seeker的求职申请审核状态等信息, 构造两条通知
     */
    void sendHrCheckDeliverMessage(SeekerDeliver seekerDeliver, Integer checkValue, String positionName, Long hrId);

    /**
     * seeker 投递简历后向双方 发送系统通知
     * @param seekerId
     * @param hrId
     * @param positionName
     */
    void sendSeekerDeliverPositionMessageSuccessMessage(Long seekerId, Long hrId, String positionName);
}

package com.home.closematch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.closematch.entity.*;
import com.home.closematch.entity.vo.CheckDeliverVo;
import com.home.closematch.exception.ServiceErrorException;
import com.home.closematch.mapper.*;
import com.home.closematch.service.NotifyService;
import com.home.closematch.service.PositionService;
import com.home.closematch.service.SeekerDeliverService;
import com.home.closematch.utils.SeekerDeliverUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class SeekerDeliverServiceImpl extends ServiceImpl<SeekerDeliverMapper, SeekerDeliver>
implements SeekerDeliverService{

    @Autowired
    private SeekerDeliverMapper seekerDeliverMapper;

    @Autowired
    private PositionService positionService;
    @Autowired
    private NotifyService notifyService;
    /**
     * 审核该条求职记录
     * @param hrId
     * @param checkDeliverVo
     */
    @Override
    public void checkDeliver(Long hrId, CheckDeliverVo checkDeliverVo) {

        SeekerDeliver seekerDeliver = seekerDeliverMapper.selectById(checkDeliverVo.getDeliverId());
        // 当 记录不存在 / 已经做出判断 / 不是规定值 (该值在接收时已经审核过)
        if (seekerDeliver == null || !seekerDeliver.getStatus().equals(SeekerDeliverUtils.STATUS_CHECK_WAIT))
            throw new ServiceErrorException(401, "违规操作, 请合规操作");
        // 后置再判断, 如果该条消息不是当前hr 的则终止操作
        Position position = positionService.getById(seekerDeliver.getPositionId());
        if (!position.getHrId().equals(hrId))
            throw new ServiceErrorException(401, "违规操作, 请合规操作");
        // 设置审批状态
        seekerDeliver.setStatus(checkDeliverVo.getCheckValue());
        seekerDeliverMapper.updateById(seekerDeliver);
        // 获取相应职位的PositionName 后续使用
        String positionName = position.getPositionName();
        notifyService.sendHrCheckDeliverMessage(seekerDeliver, checkDeliverVo.getCheckValue(), positionName, hrId);
    }

    /**
     * seeker投递申请
     *
     * @param seekerID
     * @param positionId
     */
    @Override
    public void sendDeliver(Long seekerID, Long positionId) {
        Position position = positionService.getById(positionId);
        if (position == null)
            throw new ServiceErrorException(400, "请求异常");

        SeekerDeliver seekerDeliver = new SeekerDeliver();
        seekerDeliver.setPositionId(positionId);
        seekerDeliver.setSeekId(seekerID);
        seekerDeliverMapper.insert(seekerDeliver);

        // 应该异步发通知
        notifyService.sendSeekerDeliverPositionMessageSuccessMessage(seekerID, position.getHrId(), position.getPositionName());
    }


}





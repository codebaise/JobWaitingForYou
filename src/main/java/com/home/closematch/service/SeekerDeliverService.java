package com.home.closematch.service;

import com.home.closematch.entity.SeekerDeliver;
import com.baomidou.mybatisplus.extension.service.IService;
import com.home.closematch.entity.vo.CheckDeliverVo;

/**
 *
 */
public interface SeekerDeliverService extends IService<SeekerDeliver> {
    /**
     * 审核该条求职记录
     * 如果为同意则发remind
     * @param hrId
     * @param checkDeliverVo
     */
    void checkDeliver(Long hrId, CheckDeliverVo checkDeliverVo);

    /**
     * seeker投递申请
     * @param seekerID
     * @param positionId
     */
    void sendDeliver(Long seekerID, Long positionId);
}

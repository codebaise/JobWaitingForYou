package com.home.closematch.service;

import com.home.closematch.entity.SeekerExperience;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface SeekerExperienceService extends IService<SeekerExperience> {
    /**
     * 获取seeker就职经历 ; 自己填写的和其他公司hr提交的
     * @param seekerId
     * @return
     */
    List<SeekerExperience> getSeekerExperienceInfoBySeekerId(Long seekerId);

    /**
     * 保存 / 修改相应的就职经历
     * @param seekerId
     * @param seekerExperience
     */
    void updateSeekerExperienceInfo(Long seekerId, Long experienceId, SeekerExperience seekerExperience);

    void addSeekerExperience(Long seekerId, SeekerExperience seekerExperience);
}

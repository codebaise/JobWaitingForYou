package com.home.closematch.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.closematch.entity.JobSeeker;
import com.home.closematch.entity.SeekerExperience;
import com.home.closematch.exception.ServiceErrorException;
import com.home.closematch.service.SeekerExperienceService;
import com.home.closematch.mapper.SeekerExperienceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class SeekerExperienceServiceImpl extends ServiceImpl<SeekerExperienceMapper, SeekerExperience>
implements SeekerExperienceService{
    @Autowired
    private SeekerExperienceMapper seekerExperienceMapper;
    /**
     * 获取seeker就职经历 ; 自己填写的和其他goon公司hr提交的
     *
     * @param seekerId
     * @return
     */
    @Override
    public List<SeekerExperience> getSeekerExperienceInfoBySeekerId(Long seekerId) {
        return seekerExperienceMapper.selectList(new QueryWrapper<SeekerExperience>().eq("user_id", seekerId));
    }

    /**
     * 保存 / 修改相应的就职经历
     * 只能修改自己添加的信息
     *
     * @param seekerId
     * @param seekerExperience
     */
    @Override
    public void updateSeekerExperienceInfo(Long seekerId, Long experienceId, SeekerExperience seekerExperience) {
        SeekerExperience seekerExperience1 = seekerExperienceMapper.selectById(experienceId);
        if (seekerExperience1 != null && seekerExperience1.getUserId() != seekerId)
            throw new ServiceErrorException(400, "请求异常");

        seekerExperience.setId(seekerExperience1.getId());
        seekerExperienceMapper.updateById(seekerExperience);
    }

    @Override
    public void addSeekerExperience(Long seekerId, SeekerExperience seekerExperience) {
        seekerExperience.setUserId(seekerId);
        seekerExperienceMapper.insert(seekerExperience);
    }
}





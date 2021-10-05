package com.home.closematch.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.closematch.entity.SeekerSchool;
import com.home.closematch.exception.ServiceErrorException;
import com.home.closematch.service.SeekerSchoolService;
import com.home.closematch.mapper.SeekerSchoolMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class SeekerSchoolServiceImpl extends ServiceImpl<SeekerSchoolMapper, SeekerSchool>
implements SeekerSchoolService{
    @Autowired
    private SeekerSchoolMapper seekerSchoolMapper;
    /**
     * 通过seekerId 查找对应的教育信息
     *
     * @param seekerId
     * @return
     */
    @Override
    public List<SeekerSchool> getSeekerSchoolsBySeekerId(Long seekerId) {
        return seekerSchoolMapper.selectList(new QueryWrapper<SeekerSchool>().eq("user_id", seekerId));
    }

    @Override
    public void addSeekerSchoolInfo(Long seekerId, SeekerSchool seekerSchool) {
        seekerSchool.setUserId(seekerId);
        seekerSchoolMapper.insert(seekerSchool);
    }

    @Override
    public void dropSeekerSchoolInfo(Long seekerId, Long schoolId) {
        SeekerSchool school = seekerSchoolMapper.selectById(schoolId);
        if (school == null || !school.getUserId().equals(seekerId))
            throw new ServiceErrorException(400, "操作异常");

        seekerSchoolMapper.deleteById(school.getId());
    }

    @Override
    public void updateSeekerSchoolInfo(Long seekerId, Long schoolId, SeekerSchool seekerSchool) {
        SeekerSchool school = seekerSchoolMapper.selectById(schoolId);
        if (school == null || school.getUserId().equals(seekerId))
            throw new ServiceErrorException(400, "请合规操作");
        seekerSchool.setId(school.getId());
        seekerSchoolMapper.updateById(school);

    }
}





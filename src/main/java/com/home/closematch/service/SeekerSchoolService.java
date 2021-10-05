package com.home.closematch.service;

import com.home.closematch.entity.SeekerSchool;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface SeekerSchoolService extends IService<SeekerSchool> {
    /**
     * 通过seekerId 查找对应的教育信息
     * @param seekerId
     * @return
     */
    List<SeekerSchool> getSeekerSchoolsBySeekerId(Long seekerId);

    /**
     * seeker添加/ 修改就学经历
     * @param seekerId
     * @param seekerSchool
     */
    void addSeekerSchoolInfo(Long seekerId, SeekerSchool seekerSchool);

    void dropSeekerSchoolInfo(Long seekerId, Long schoolId);

    void updateSeekerSchoolInfo(Long seekerId, Long schoolId, SeekerSchool seekerSchool);

}

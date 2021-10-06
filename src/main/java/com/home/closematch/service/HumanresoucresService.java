package com.home.closematch.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.home.closematch.entity.Account;
import com.home.closematch.entity.dto.HrCompanyDTO;
import com.home.closematch.entity.Humanresoucres;
import com.baomidou.mybatisplus.extension.service.IService;
import com.home.closematch.entity.Position;
import com.home.closematch.entity.dto.SeekerAccountSchoolDTO;
import com.home.closematch.entity.vo.CheckDeliverVo;
import com.home.closematch.entity.vo.SeekAndPositionVo;

import java.util.List;

/**
 *
 */
public interface HumanresoucresService extends IService<Humanresoucres> {
    /**
     * 获取hr列表 admin 行为
     *
     * @param page
     * @return
     */
    IPage<HrCompanyDTO> getHrWithCompanyList(Integer page);

    /**
     * 获取hr自己的信息和相应的公司信息
     *
     * @return
     */
    HrCompanyDTO getHrInfoWithCompany(Account account);

    /**
     * 查看当前hr个人信息填写的情况
     * 只要不是0就行, 不是0说明已经填写了相应的信息
     */
    boolean checkHrInfoEnrolCondition(Long hrId);
}

package com.home.closematch.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.closematch.entity.*;
import com.home.closematch.entity.dto.HrCompanyDTO;
import com.home.closematch.exception.ServiceErrorException;
import com.home.closematch.mapper.*;
import com.home.closematch.service.CompanyService;
import com.home.closematch.service.HumanresoucresService;
import com.home.closematch.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class HumanresoucresServiceImpl extends ServiceImpl<HumanresoucresMapper, Humanresoucres>
        implements HumanresoucresService {
    @Autowired
    private HumanresoucresMapper humanresoucresMapper;
    @Autowired
    private CompanyService companyService;

    /**
     * 获取hr列表 admin 行为
     *
     * @param page
     * @return
     */
    @Override
    public IPage<HrCompanyDTO> getHrWithCompanyList(Integer page) {
        return humanresoucresMapper.selectHrWithCompany(new Page<>(page, CommonUtils.perPageSize));
    }

    /**
     * 获取hr自己的信息和相应的公司信息
     *
     * @return
     */
    @Override
    public HrCompanyDTO getHrInfoWithCompany(Account account) {
        // get hr obj
        Humanresoucres humanresoucres = getById(account.getUserId());
        if (humanresoucres == null)
            throw new ServiceErrorException(400, "请填写用户信息");
        // 填充 DTO
        HrCompanyDTO hrCompanyDTO = new HrCompanyDTO();
        hrCompanyDTO.setAccount(account);
        hrCompanyDTO.setHr(humanresoucres);
        // 公司不是必须的, 所以在if内填充
        Company company = null;
        if (humanresoucres.getCompanyId() != 0) {
            company = companyService.getById(humanresoucres.getCompanyId());
//            company = companyMapper.selectById(humanresoucres.getCompanyId());
            if (company != null)
                hrCompanyDTO.setCompany(company);
        }

        return hrCompanyDTO;
    }

    /**
     * 查看当前hr个人信息填写的情况
     * 只要不是0就行, 不是0说明已经填写了相应的信息
     */
    @Override
    public boolean checkHrInfoEnrolCondition(Long hrId) {
        Humanresoucres humanresoucres = humanresoucresMapper.selectById(hrId);
        return humanresoucres.getStatus() != 0;
    }
}





package com.home.closematch.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.home.closematch.entity.dto.HrCompanyDTO;
import com.home.closematch.entity.Humanresoucres;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Entity com.home.closematch.entity.Humanresoucres
 */
@Repository
public interface HumanresoucresMapper extends BaseMapper<Humanresoucres> {
    IPage<HrCompanyDTO> selectHrWithCompany(Page<HrCompanyDTO> page);
}





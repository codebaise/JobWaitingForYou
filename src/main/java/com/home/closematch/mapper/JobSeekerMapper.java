package com.home.closematch.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.home.closematch.entity.dto.SeekerAccountSchoolDTO;
import com.home.closematch.entity.JobSeeker;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.home.closematch.entity.Position;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Entity com.home.closematch.entity.JobSeeker
 */
@Repository
public interface JobSeekerMapper extends BaseMapper<JobSeeker> {

    IPage<SeekerAccountSchoolDTO> selectSeekerListWithAccount(Page page);

    List<Position> selectSeekerDeliverWithPosition(Long userId);
}





package com.home.closematch.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.closematch.entity.*;
import com.home.closematch.entity.dto.SeekerAccountSchoolDTO;
import com.home.closematch.entity.vo.AdvantageVo;
import com.home.closematch.entity.vo.SeekerBaseInfoVo;
import com.home.closematch.entity.vo.SeekerExpectVo;
import com.home.closematch.exception.ServiceErrorException;
import com.home.closematch.mapper.*;
import com.home.closematch.service.*;
import com.home.closematch.utils.CommonUtils;
import com.home.closematch.utils.SeekerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *
 */
@Service
public class JobSeekerServiceImpl extends ServiceImpl<JobSeekerMapper, JobSeeker>
implements JobSeekerService{

    @Autowired
    private JobSeekerMapper jobSeekerMapper;

    @Autowired
    private SeekerExperienceService seekerExperienceService;
    @Autowired
    private SeekerDeliverService seekerDeliverService;
    @Autowired
    private SeekerSchoolService seekerSchoolService;
    @Autowired
    private PositionService positionService;


    /**
     * 根据求职者id获取当前求职者已经递交的求职申请
     */
    @Override
    public List<Position> getSeekerDeliverWithPosition(Long seekerId) {
        return jobSeekerMapper.selectSeekerDeliverWithPosition(seekerId);
    }

    /**
     * 获取求职者工作经历
     */
    @Override
    public List<SeekerExperience> getSeekerWorkExperiences(Long seekerId) {
        return seekerExperienceService.list(new QueryWrapper<SeekerExperience>().eq("user_id", seekerId));
//        return seekerExperienceMapper.selectList(new QueryWrapper<SeekerExperience>().eq("user_id", seekerId));
    }

    /**
     * 获取所有的求职者账户, 教学信息 Admin 行为
     */
    @Override
    public IPage<SeekerAccountSchoolDTO> getSeekerListWithAccount(Integer page) {
        return jobSeekerMapper.selectSeekerListWithAccount(new Page<>(page, CommonUtils.perPageSize));
    }

    /**
     * 检查当前用户和hr的关系
     * 只有在没有处理的时候才能进行求职者的信息查看, 如果已经拒绝了该求职用户, 那么无法再查看当前求职者的信息
     */
    @Override
    public SeekerDeliver checkSeekerRelationOfHr(Long hrId, Long deliverId) {
        SeekerDeliver seekerDeliver = seekerDeliverService.getById(deliverId);
        // 检查该条求职信息是否存在
        if (seekerDeliver == null)
            throw new ServiceErrorException(401, "违规操作, 请勿修改参数");
            // 如果存在, 则判断是否已经被pass
        else if (seekerDeliver.getStatus() == 1)
            throw new ServiceErrorException(401, "您已拒绝该请求, 请联系~");
        // 检查position 是否属于该user
        Position position = positionService.getById(seekerDeliver.getPositionId());
//        Position position = positionMapper.selectById();
        if (position == null || !position.getHrId().equals(hrId))
            throw new ServiceErrorException(401, "违规操作, 该求职信息不属于你");
        return seekerDeliver;
    }

    @Override
    public List<SeekerExperience> hrGetSeekerWorkExperience(Long hrId, Long deliverId) {
        SeekerDeliver seekerDeliver = checkSeekerRelationOfHr(hrId, deliverId);
        return getSeekerWorkExperiences(seekerDeliver.getSeekId());
    }

    /**
     * 添加/修改 seekerInfo
     *  @param seekerId
     * @param seekerBaseInfoVo
     */
    @Override
    public void saveOrUpdateSeekerInfo(long seekerId, SeekerBaseInfoVo seekerBaseInfoVo) {
        JobSeeker originalJobSeeker = jobSeekerMapper.selectById(seekerId);
        JobSeeker jobSeeker = new JobSeeker();
        jobSeeker.setName(seekerBaseInfoVo.getName());

        jobSeeker.setAge(SeekerUtils.calculateYear(seekerBaseInfoVo.getBirthday(), new Date()));
        jobSeeker.setUserIdentity(seekerBaseInfoVo.getUserIdentity());
        jobSeeker.setCurrentStatus(seekerBaseInfoVo.getCurrentStatus());
        jobSeeker.setId(originalJobSeeker.getId());
        jobSeekerMapper.updateById(jobSeeker);
    }

    /**
     * 投递申请
     *
     * @param seekerId
     * @param positionId
     */
    @Override
    public void deliverResume(Long seekerId, Long positionId) {
    }

    @Override
    public void editAdvantage(Long seekerId, AdvantageVo advantageVo) {
        JobSeeker jobSeeker = new JobSeeker();
        jobSeeker.setId(seekerId);
        jobSeeker.setAdvantage(advantageVo.getAdvantage());
        jobSeekerMapper.updateById(jobSeeker);
    }

    @Override
    public void editExpect(Long seekerId, SeekerExpectVo seekerExpectVo) {
        JobSeeker jobSeeker = new JobSeeker();
        jobSeeker.setId(seekerId);
        jobSeeker.setExpectPosition(seekerExpectVo.getExpectPosition());
        jobSeeker.setExpectCity(seekerExpectVo.getExpectCity());
        jobSeeker.setMinExpectSalary(seekerExpectVo.getMinExpectSalary());

        jobSeekerMapper.updateById(jobSeeker);
    }

    /**
     * hr 去查看相应求职记录的seeker的信息
     * 需要进行相应的判断, 判断是否已经处理过:
     * 如果已经pass那么 无权再查看详细信息, 后期或许可以给予一个手机号, 让其主动联系
     *
     * @param seekDeliverId
     */
    @Override
    public SeekerAccountSchoolDTO hrGetSeekerDetailInfo(Long userId, Long seekDeliverId) {
        SeekerDeliver seekerDeliver = checkSeekerRelationOfHr(userId, seekDeliverId);
        JobSeeker jobSeeker = jobSeekerMapper.selectById(seekerDeliver.getSeekId());
        SeekerSchool seekerSchool = seekerSchoolService.getOne(new QueryWrapper<SeekerSchool>().eq("user_id", jobSeeker.getId()));
//        SeekerSchool seekerSchool = seekerSchoolMapper.selectOne();
        SeekerAccountSchoolDTO seekerAccountSchoolDTO = new SeekerAccountSchoolDTO();
        seekerAccountSchoolDTO.setSeeker(jobSeeker);
        seekerAccountSchoolDTO.setSchool(seekerSchool);
        return seekerAccountSchoolDTO;
    }
}





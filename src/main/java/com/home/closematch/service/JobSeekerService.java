package com.home.closematch.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.home.closematch.entity.SeekerDeliver;
import com.home.closematch.entity.dto.SeekerAccountSchoolDTO;
import com.home.closematch.entity.JobSeeker;
import com.baomidou.mybatisplus.extension.service.IService;
import com.home.closematch.entity.Position;
import com.home.closematch.entity.SeekerExperience;
import com.home.closematch.entity.vo.AdvantageVo;
import com.home.closematch.entity.vo.SeekerBaseInfoVo;
import com.home.closematch.entity.vo.SeekerExpectVo;

import java.util.List;

/**
 *
 */
public interface JobSeekerService extends IService<JobSeeker> {
    /**
     * 根据求职者id获取当前求职者已经递交的求职申请
     * @param seekerId seekerId
     * @return
     */
    List<Position> getSeekerDeliverWithPosition(Long seekerId);

    /**
     * 获取当前求职者的工作经验, 自己填写的和入职的公司添加的
     * @param seekerId
     * @return
     */
    List<SeekerExperience> getSeekerWorkExperiences(Long seekerId);

    /**
     * 获取所有的求职者账户, 教学信息 Admin 行为
     * @param page
     * @return
     */
    IPage<SeekerAccountSchoolDTO> getSeekerListWithAccount(Integer page);

    /**
     * hr 去查看相应求职记录的seeker的信息
     * 需要进行相应的判断, 判断是否已经处理过:
     *  如果已经pass那么 无权再查看详细信息, 后期或许可以给予一个手机号, 让其主动联系
     * @param seekDeliverId
     */
    SeekerAccountSchoolDTO hrGetSeekerDetailInfo(Long userId, Long seekDeliverId);

    /**
     * 检查当前用户和hr的关系
     * @return 如果存在则返回一个SeekerDeliver对象
     */
    SeekerDeliver checkSeekerRelationOfHr(Long hrId, Long deliverId);

    /**
     * hr 去获取当前求职者的工作经历
     * @param hrId
     * @param deliverId
     * @return
     */
    List<SeekerExperience> hrGetSeekerWorkExperience(Long hrId, Long deliverId);

    /**
     * 添加/修改 seekerInfo
     * @param seekerId
     * @param seekerBaseInfoVo
     */
    void saveOrUpdateSeekerInfo(long seekerId, SeekerBaseInfoVo seekerBaseInfoVo);

    /**
     * 投递申请
     */
    void deliverResume(Long seekerId, Long positionId);

    void editAdvantage(Long seekerId, AdvantageVo advantageVo);
    void editExpect(Long seekerId, SeekerExpectVo seekerExpectVo);
}

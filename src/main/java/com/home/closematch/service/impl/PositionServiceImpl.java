package com.home.closematch.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.closematch.entity.*;
import com.home.closematch.entity.dto.SeekerPositionCommentDTO;
import com.home.closematch.entity.vo.PositionDetailInfoVo;
import com.home.closematch.entity.vo.PositionsVo;
import com.home.closematch.entity.vo.SeekAndPositionVo;
import com.home.closematch.exception.ServiceErrorException;
import com.home.closematch.mapper.PositionMapper;
import com.home.closematch.pojo.PagePositionsVo;
import com.home.closematch.service.*;
import com.home.closematch.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 */
@Service
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position>
        implements PositionService {

    @Autowired
    private PositionMapper positionMapper;

    @Autowired
    private Hr2staffCommentService hr2staffCommentService;
    @Autowired
    private SeekerDeliverService seekerDeliverService;
    @Autowired
    private JobSeekerService jobSeekerService;
    @Autowired
    private HumanresoucresService humanresoucresService;
    @Autowired
    private CompanyService companyService;

    /**
     * 根据职位的Id 改变职位的状态是否可用, admin行为
     *
     * @param positionId
     * @param status
     */
    @Override
    public void changePositionStatusByPositionId(Long positionId, Integer status){
        Position position = positionMapper.selectById(positionId);
        if (position == null)
            throw new ServiceErrorException(401, "Error Update Operation");

        position.setStatus(status);
        positionMapper.updateById(position);
    }

    /**
     * 获取当前hr 发布的所有职位的所有的应聘者
     *
     * @param hrId
     * @return
     */
    @Override
    public List<SeekAndPositionVo> getApplicantsWithPosition(Long hrId) {
        // 查询所有的hr发布的position
        List<Position> positionList = positionMapper.selectList(new QueryWrapper<Position>().eq("hr_id", hrId));
        ArrayList<Long> positionIdList = new ArrayList<>();
        // 为下面查询相应的deliver 记录添加in 属性
        for (Position position : positionList) {
            positionIdList.add(position.getId());
        }
        if (positionIdList.size() == 0){
            throw new ServiceErrorException(400, "暂未发布职位");
        }
        // 使用in查询 发布的相关的职位的所有投递记录
        QueryWrapper<SeekerDeliver> seekerDeliverQueryWrapper = new QueryWrapper<SeekerDeliver>().in("position_id", positionIdList).orderByDesc("create_time");
        List<SeekerDeliver> seekerDeliverList = seekerDeliverService.list(seekerDeliverQueryWrapper);
        ArrayList<Long> seekerIdList = new ArrayList<>();
        // 为了使用in查询相应的seeker
        for (SeekerDeliver seekerDeliver : seekerDeliverList) {
            seekerIdList.add(seekerDeliver.getSeekId());
        }
        if (seekerDeliverList.size() <= 0)
            return null;
        // 查询上面对应的seekers
        List<JobSeeker> jobSeekers = jobSeekerService.listByIds(seekerIdList);
        // return 的结果集
        ArrayList<SeekAndPositionVo> seekAndPositionVos = new ArrayList<>();
        // 外层遍历被投递的所有记录
        for (SeekerDeliver seekerDeliver : seekerDeliverList) {
            // 每一条记录都会在两个表对应属性, 所以直接在这里创建
            SeekAndPositionVo seekAndPositionVo = new SeekAndPositionVo();
            // set 与deliver 记录相关的属性
            seekAndPositionVo.setDeliverId(seekerDeliver.getId());
            seekAndPositionVo.setStatus(seekerDeliver.getStatus());
            // 找到这条求职信息 的职位详情, 不能进行删除, 因为一个人可以对一个职位投递多次
            for (Position position : positionList) {
                if (position.getId().equals(seekerDeliver.getPositionId())) {
                    seekAndPositionVo.setPositionName(position.getPositionName());
                    seekAndPositionVo.setWorkCity(position.getWorkCity());
                    break;
                }
            }
            // 找到这条求职信息对应的seeker
            for (JobSeeker jobSeeker : jobSeekers) {
                if (jobSeeker.getId().equals(seekerDeliver.getSeekId())) {
                    seekAndPositionVo.setSeekerName(jobSeeker.getName());
                    seekAndPositionVo.setUserIdentity(jobSeeker.getUserIdentity());
                    seekAndPositionVo.setCurrentStatus(jobSeeker.getCurrentStatus());
                    break;
                }
            }
            seekAndPositionVos.add(seekAndPositionVo);
        }
        return seekAndPositionVos;
    }

    /**
     * hr 查看其他hr对当前求职者的评论信息
     * 获取positionId 对应的评论
     */
    @Override
    public List<SeekerPositionCommentDTO> getPositionComments(Long seekerId, Long positionId) {
        return hr2staffCommentService.getSeekerPositionComments(seekerId, positionId);
    }

    /**
     * 分页获取所有职位列表
     * TODO : 加入筛选功能
     *
     * @param page
     * @return
     */
    @Override
    public PagePositionsVo getPositions(int page) {
        // 分页查询职位
        PagePositionsVo pagePositionsVo = positionMapper.selectPage(new PagePositionsVo(page, CommonUtils.perPageSize),
                new QueryWrapper<Position>().eq("status", 2));
        // 存储 companyId和 一个company对象
        HashMap<Long, Company> companyInfoMap = new HashMap<>();
        // 存储hr id 和hr对象
        HashMap<Long, Humanresoucres> hrInfoMap = new HashMap<>();
        // 结果集
        ArrayList<PositionsVo> positionsVos = new ArrayList<>();
        // 遍历position集合
        pagePositionsVo.getRecords().forEach(position -> {
            Humanresoucres hr = hrInfoMap.get(position.getHrId());;
            if (hr == null) {
                hr = humanresoucresService.getById(position.getHrId());
                hrInfoMap.put(hr.getId(), hr);
            }

            Company company = companyInfoMap.get(hr.getCompanyId());
            if (company == null){
                company = companyService.getById(hr.getCompanyId());
                companyInfoMap.put(company.getId(), company);
            }
            positionsVos.add(new PositionsVo(hr, position, company));
        });
        pagePositionsVo.setPositionsVos(positionsVos);
        pagePositionsVo.setRecords(null);
        return pagePositionsVo;
    }

    /**
     * 获取职位的详情信息
     * 就是在职位列表点进去之后就可以查看到的信息
     * 其实再company的那里, 有一个类似方法, 中间方法体基本一样, 返回的是list 所以不适用
     *
     * @param positionId
     */
    @Override
    public PositionDetailInfoVo getPositionDetailInfoById(Long positionId) {
        Position position = positionMapper.selectById(positionId);
        if (position == null)
            throw new ServiceErrorException(400, "Error Operation");

        Humanresoucres hr = humanresoucresService.getById(position.getHrId());
        if (hr == null)
            throw new ServiceErrorException(400, "Error Operation");

        return new PositionDetailInfoVo(hr, position);
    }


    /**
     * hr 更改发布的职位的启用状态
     */
    @Override
    public void changePositionUseStatus(Long hrId, Long positionId) {
        Position position = positionMapper.selectOne(new QueryWrapper<Position>()
                .eq("hr_id", hrId)
                .eq("id", positionId));
        if (position == null)
            throw new ServiceErrorException(400, "未获取到相应职位信息");
        Integer status = position.getStatus();
        // 未审核通过 / 被 禁止时
        if (status <= 1)
            throw new ServiceErrorException(401, "无权操作, 请重新填写相应的职位信息");
        else if (status == 2 || status == 3) {
            position.setStatus(4);
        } else
            position.setStatus(2);
        positionMapper.updateById(position);
    }

    /**
     * 新增 / 更新相应的职位信息
     * 这里需要先判断以下hr信息审核情况
     *
     * @param hrId
     * @param position
     */
    @Override
    public void saveOrUpdatePosition(Long hrId, Position position) {
        boolean result = humanresoucresService.checkHrInfoEnrolCondition(hrId);
        // 如果 = 0 说明还没有填写信息, 那么是允许插入一条记录的
        if (!result) {
            Integer hr_id = positionMapper.selectCount(new QueryWrapper<Position>().eq("hr_id", hrId));
            // 判断已经发布职位是否为 0
            if (hr_id != 0)
                throw new ServiceErrorException(400, "未完成资料审核, 只能先发布一个职位, 请先完成资料审核 !!!");
        }
        position.setHrId(hrId);
        // 如果是新增, 则直接插入即可
        if (position.getId() == null)
            positionMapper.insert(position);

        // 如果是修改, 那么先查一下有没有这一条记录
        Position position1 = positionMapper.selectById(position.getId());
        if (position1 == null || !hrId.equals(position1.getHrId()))
            throw new ServiceErrorException(401, "违规操作, 请勿修改相关信息");

        positionMapper.updateById(position);
    }
   // 获取 hrId 对应的hr发布的所有职位
    @Override
    public List<Position> getPublishPosition(Long hrId) {
        return positionMapper.selectList(new QueryWrapper<Position>()
                .eq("hr_id", hrId)
                .orderByDesc("create_time"));
    }

//    @Override
//    public List<Position> getHrPublishPositions(Long hrId) {
//        return positionMapper.selectList(new QueryWrapper<Position>().eq("hr_id", hrId));
//    }
}





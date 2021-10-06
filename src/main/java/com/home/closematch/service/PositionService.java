package com.home.closematch.service;

import com.home.closematch.entity.Position;
import com.baomidou.mybatisplus.extension.service.IService;
import com.home.closematch.entity.dto.SeekerPositionCommentDTO;
import com.home.closematch.entity.vo.PositionDetailInfoVo;
import com.home.closematch.entity.vo.SeekAndPositionVo;
import com.home.closematch.pojo.PagePositionsVo;

import java.util.List;

/**
 *
 */
public interface PositionService extends IService<Position> {
//    /**
//     * pass reason : 在controller 验证, 减少service耦合
//     * hr 去 获取求职信息 对应的职位的评论
//     *  需要进行验证
//     * @param accountId
//     * @param seekerId
//     * @param positionId
//     * @return
//     */
//    List<SeekerPositionCommentDTO> hrGetWorkExperienceComment(Long seekerId, Long positionId);

//    /** 重复了
//     *     实际中被
//     */
//    List<Position> getHrPublishPositions(Long hrId);

    /**
     * 获取userId 对应的 hr 发布的所有职位的所有的应聘者
     *
     * @param hrId
     * @return
     */
    List<SeekAndPositionVo> getApplicantsWithPosition(Long hrId);

    /**
     * 根据职位的Id 改变职位的状态是否可用, admin行为
     *
     * @param positionId
     * @param status
     */
    void changePositionStatusByPositionId(Long positionId, Integer status);

    /**
     * 获取 hrId 对应的hr发布的所有职位
     *
     * @param hrId
     * @return
     */
    List<Position> getPublishPosition(Long hrId);

    /**
     * hr 更改发布的职位的启用状态
     *
     * @param userId
     * @param positionId
     */
    void changePositionUseStatus(Long hrId, Long positionId);

    /**
     * hr 新增 / 更新相应的职位信息
     * 这里需要先判断以下hr信息审核情况
     *
     * @param hrId
     * @param position
     */
    void saveOrUpdatePosition(Long hrId, Position position);

    /**
     * 获取positionId 对应的评论
     *
     * @param seekerId
     * @return
     */
    List<SeekerPositionCommentDTO> getPositionComments(Long seekerId, Long positionId);

    /**
     * 分页获取职位列表, 后期加入筛选功能
     * @param page
     * @return
     */
    PagePositionsVo getPositions(int page);

    /**
     * 获取职位的详情信息
     * 就是在职位列表点进去之后就可以查看到的信息
     * 其实再company的那里, 有一个类似方法, 中间方法体基本一样, 返回的是list 所以不适用
     */
     PositionDetailInfoVo getPositionDetailInfoById(Long positionId);
}

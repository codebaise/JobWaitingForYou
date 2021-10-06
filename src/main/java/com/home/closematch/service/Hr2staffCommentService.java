package com.home.closematch.service;

import com.home.closematch.entity.Hr2staffComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.home.closematch.entity.dto.SeekerPositionCommentDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 */
public interface Hr2staffCommentService extends IService<Hr2staffComment> {
    /**
     * hr对当前员工的评价
     */
    List<SeekerPositionCommentDTO> getSeekerPositionComments(Long seekerId, Long positionId);
}
